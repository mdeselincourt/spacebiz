package com.google.gwt.sample.stockwatcher.client;

import com.google.gwt.sample.stockwatcher.shared.FieldVerifier;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class StockWatcher implements EntryPoint {

	// SERVER INTEGRATION: Asynchronous callback interfaces: factory-build a service at runtime via GWT
	private MarketPriceServiceAsync marketPriceSvc = GWT.create(MarketPriceService.class);

	// Page classes
	private Content marketPage = new MarketPage();

	// DECLARE PAGE WIDGETS
	// Doing this here so inner classes (handlers) can see them
	Label companyIdentityEntryLabel;
	TextBox nameField;
	TextBox tickerField;
	Label foundingEquityLabel;
	TextBox foundingEquityField;
	Label foundingEquityOutcomeLabel;
	Label foundingLoanLabel;
	TextBox foundingLoanField;
	Label foundingLoanOutcomeLabel;
	Label foundingCapitalLabel;
	Button foundCompanyButton;
	Button marketPageButton;	


	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network " + "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		Logger logger = Logger.getLogger("SpaceBiz");
		logger.log(Level.INFO, "Client code executing in browser. onModuleLoad() is running.");
		go();
	}

	// TODO: This all needs to be refactored into a Content class now probably.
	public void go() {

		instantiateWidgets();

		configurePage();
		
		assemblePage();

	} // end of Go() (to build the UI)

	private void instantiateWidgets() {

		// Instantiate widgets
		//// Company Identity
		// field for accepting company name
		nameField = new TextBox();
		nameField.setText("Acme");

		// field for accepting ticker code
		tickerField = new TextBox();
		tickerField.setText("ACM");

		// label for introducing / erroring company name and ticker
		companyIdentityEntryLabel = new Label();
		companyIdentityEntryLabel.setText("Please enter your company name and ticker code:");

		foundingCapitalLabel = new Label("£-");

		// button for founding company
		foundCompanyButton = new Button("Found");
		foundCompanyButton.addStyleName("sendButton");

		// button for switching to market view
		marketPageButton = new Button("Market Page");

		//// Finance loans

		foundingLoanLabel = new Label("Select a loan up to £5000:");
		foundingLoanField = new TextBox();
		foundingLoanField.setText("0");		
		foundingLoanOutcomeLabel = new Label("£0 interest per annum");

		//// Finance equity

		foundingEquityLabel = new Label("Select an equity % of up to 49:");
		foundingEquityField = new TextBox();
		foundingEquityField.setText("0");		
		foundingEquityOutcomeLabel = new Label("£0 equity");

		//// Capital raised summary

		foundingCapitalLabel = new Label("£-");

		// button for founding company
		foundCompanyButton = new Button("Found");
		foundCompanyButton.addStyleName("sendButton");

		// button for switching to market view
		marketPageButton = new Button("Market Page");

		// End of Instantiating widgets

	}


	
	private void configurePage() {
		// Focus the cursor on the name field when the app loads
		nameField.setFocus(true);
		nameField.selectAll();

		// Create a handler to handle loan box changes and attach it to the field
		FinanceInputHandler loanInputHandler = new FinanceInputHandler();
		foundingLoanField.addKeyUpHandler(loanInputHandler);
		foundingEquityField.addKeyUpHandler(loanInputHandler);

		// Add my dedicated handler to the page button
		PageButtonHandler marketButtonHandler = new PageButtonHandler("market");
		marketPageButton.addClickHandler(marketButtonHandler);
	}
	
	private void assemblePage() {
		
		RootPanel appDivRootPanel = RootPanel.get("content");
		
		// Assemble page
		appDivRootPanel.add(companyIdentityEntryLabel);
		appDivRootPanel.add(nameField);
		appDivRootPanel.add(tickerField);

		appDivRootPanel.add(foundingLoanLabel);
		appDivRootPanel.add(foundingLoanField);
		appDivRootPanel.add(foundingLoanOutcomeLabel);

		appDivRootPanel.add(foundingEquityLabel);
		appDivRootPanel.add(foundingEquityField);
		appDivRootPanel.add(foundingEquityOutcomeLabel);

		appDivRootPanel.add(foundingCapitalLabel);

		appDivRootPanel.add(foundCompanyButton);
		appDivRootPanel.add(marketPageButton);

		//// Load other panels ////
		ContentContainer.getInstance().setDivContent(new LoginPage(), "login");		
	}

	// Handler to take care of financial input events 
	class FinanceInputHandler implements KeyUpHandler {

		/**
		 * Fired when the user finishes hitting Enter
		 */
		public void onKeyUp(KeyUpEvent event) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {

				int loan = (Integer.parseInt(foundingLoanField.getText()));
				double interest = loan * 0.098;
				String lt = "Select a loan up to £5000:";

				// Client-side validation for user convenience
				if (loan > 5000) {
					lt = "The lender will not loan you more than £5000:";
					foundingLoanField.setText("5000");
					loan = 5000;
					interest = loan * 0.098;
				}
				else if (loan < 0) {
					foundingLoanField.setText("0");
					loan = 0;
					interest = 0;
				}

				foundingLoanLabel.setText(lt);
				foundingLoanOutcomeLabel.setText("£" + interest + " interest per annum");

				int equityPc = (Integer.parseInt(foundingEquityField.getText()));
				int equity = equityPc * 100;
				String et = "Select an equity issue % up to 49:";

				// Client-side validation for user convenience
				if (equityPc > 49) {
					et = "You cannot issue more than 49% equity without losing control of the company";
					foundingEquityField.setText("49");
					equityPc = 49;
					equity = equityPc * 100;
				}
				else if (equityPc < 0) {
					foundingEquityField.setText("0");
					equityPc = 0;
					equity = 0;						
				}

				foundingEquityLabel.setText(et);
				foundingEquityOutcomeLabel.setText("£" + equity + " raised by selling " + equityPc + " of 100 shares");

				foundingCapitalLabel.setText("£" + (equity + loan) + " total capital raised");
			}
		}
	}

	class PageButtonHandler implements ClickHandler {

		String page;

		public PageButtonHandler(String p) { page = p; } 

		public void onClick(ClickEvent event) {

			if (page == "market")
			{
				ContentContainer.getInstance().setDivContent(new MarketPage(), "content");
			}
		}
	}

}
