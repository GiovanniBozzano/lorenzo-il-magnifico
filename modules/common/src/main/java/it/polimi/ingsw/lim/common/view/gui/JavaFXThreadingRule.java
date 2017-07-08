package it.polimi.ingsw.lim.common.view.gui;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import javax.swing.*;
import java.util.concurrent.CountDownLatch;

/**
 * SOURCE: http://andrewtill.blogspot.it/2012/10/junit-rule-for-javafx-controller-testing.html
 *
 * A JUnit {@link Rule} for running tests on the JavaFX thread and performing
 * JavaFX initialisation.  To include in your test case, add the following
 * code:
 *
 * <pre>
 * {@literal @}Rule
 * public JavaFXThreadingRule jfxRule = new JavaFXThreadingRule();
 * </pre>
 *
 * @author Andy Till
 */
public class JavaFXThreadingRule implements TestRule
{
	/**
	 * Flag for setting up the JavaFX, we only need to do this once for all
	 * tests.
	 */
	private static boolean javaFxIsSetup;

	@Override
	public Statement apply(Statement statement, Description description)
	{
		return new OnJFXThreadStatement(statement);
	}

	private static class OnJFXThreadStatement extends Statement
	{
		private final Statement statement;
		private Throwable rethrownException = null;

		OnJFXThreadStatement(Statement aStatement)
		{
			this.statement = aStatement;
		}

		@SuppressWarnings("squid:S2696")
		@Override
		public void evaluate() throws Throwable
		{
			if (!JavaFXThreadingRule.javaFxIsSetup) {
				this.setupJavaFX();
				JavaFXThreadingRule.javaFxIsSetup = true;
			}
			final CountDownLatch countDownLatch = new CountDownLatch(1);
			Platform.runLater(() -> {
				try {
					this.statement.evaluate();
				} catch (Throwable e) {
					this.rethrownException = e;
				}
				countDownLatch.countDown();
			});
			countDownLatch.await();
			// if an exception was thrown by the statement during evaluation,
			// then re-throw it to fail the test
			if (this.rethrownException != null) {
				throw this.rethrownException;
			}
		}

		void setupJavaFX() throws InterruptedException
		{
			final CountDownLatch latch = new CountDownLatch(1);
			SwingUtilities.invokeLater(() -> {
				// initializes JavaFX environment
				new JFXPanel();
				latch.countDown();
			});
			latch.await();
		}
	}
}