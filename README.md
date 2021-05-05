<h1 align = "center">My Fancy Bank</h1>

---

> # Description
>
> 
>
> Project Developers: **Yuqiu Lin** and **Xiongxin Zeng**.

---

---
## BankManagementSystem Introduction
* User(Both customer and banker) are identified by email address & user type, which means one email can only register once(You can use the same email to register as one customer and one banker, but you can't register as 2 different customers)
* One customer can only have one saving account,one checking account,one loan account and one stock account.
* The bank will pay interest to certain saving accounts every day.
* All types of account can have 3 currencies, but only checking account can exchange.
* About creating account:
  * customer need to pay $10.
  * Loan Account & Security Account can only be paid from Saving account, which means if customer want to create those accounts, they must have a saving account.
  * Checking account & Saving account can be paid from the other account or cash(deposit).
* About closing account:
  * customer need to pay $10.
  * Loan Account can be closed when there is no loan and saving account have enough USD to pay the fee.
  * Security Account can be closed when there is no stock position, and the balance of all currencies should all be 0. And saving account have enough USD to pay the fee.
  * Saving account can be closed when there is no loan account and security account, but there must be a checking account, and the balance should be all 0. And checking account have enough USD to pay the fee.
  * Checking account can close if the balance of all currencies is 0 and saving account have enough USD to pay the fee. If the customer only have this one account and the money in this account can pay the fee(any currencies), then pay the fee and give the balance to the customer and close the account.
* Customer can upload certificate of collateral to ask banker to valuate. And take loan using the valuated collateral.
* Loan account can only be repaid by saving account. And the money customer get after take loan will transfer to saving account automatically.
* Security account can only transfer in from and transfer out to saving account. But transfer in operation need to over 1000USD(or the equivalent in other currencies) & the saving account should have at least 5000USD before transfer and have at least 2500USD remaining after transfer.

## how to run
1. add `mysql-connector-java-8.0.20.jar` to dependencies
2. open mysql.Run following command.
    ```
   create database mybank;
   use mybank;
   source /filePath/mybank.sql;
   ```
3. change `username` and `password` to your mysql username and password in `jdbc.properties` file
4. run Main in IDE or run following commands in terminal:
   ```
   javac *.class
   java Main
   ```

## Classes of the project
* **Main**: Main class. Then entrance of the program.<br><br>
  
* **CustomerDao**: Customer DAO. Get Data from customer table.
* **AccountDao**: Account DAO. Get Data from account table.
* **StockDao**: Stock DAO. Get Data from stock table.
* **CollateralDao**: Collateral DAO. Get Data from collateral & collateralValuation table.
* **LoanDao**: Collateral DAO. Get Data from Loan table.
* **TransactionDao**: : Collateral DAO. Get Data from Transaction table.
* **BankerDao**: : Banker DAO. Get Data from banker table.
* **InterestUpdate**: InterestUpdate class. Update data. Pay interest to saving account and add interest to loan account.<br><br>

* **User**: User class. Generate an unique id.
* **Customer extends User**: Customer class. 
* **Banker extends User**: Banker class.<br><br>

* **Account**: Account class. Have 3 currencies.
   * **CheckingAccount extends Account implements CanDeposit, CanWithdraw, CanExchange, CanTransferWithin, CanTransferToOthers**: Checking account. Can do almost everything, but need to pay transaction fee.
   * **SavingAccount extends Account implements CanDeposit, CanTransferWithin**: Saving account. Can transfer to checking account, can deposit. Can get interest if the balance is large.
   * **LoanAccount extends Account**: Loan account. Can take loan, upload collateral request and repay the loan.
   * **SecurityAccount extends Account**: Security account. can buy or sell stock<br><br>
* ***CanDeposit**: CanDeposit interface. Account that can deposit*
* ***CanExchange**: CanExchange interface. Account that can exchange*
* ***CanTransferToOthers**: CanTransferToOthers interface. Account that can transfer to other customer's account*
* ***CanTransferWithin**: CanTransferWithin interface. Account that can transfer to other account of this customer*
* ***CanWithdraw**: CanWithdraw interface. Account that can withdraw*<br><br>

* ***Transaction**: Transaction interface. Can execute*
   * **AbstractTransaction implement Transaction**: Abstract Normal Transaction class. Transaction between account. Has from account and to account
      * **Withdraw extends AbstractTransaction**: Withdraw transaction. For CanWithdraw account.
      * **Deposit extends AbstractTransaction**: Deposit transaction.For CanDeposit account.
      * **Exchange extends AbstractTransaction**: Exchange transaction.For CanExchange account.
      * **TakeLoan extends AbstractTransaction**: TakeLoan transaction. For Loan account.
      * **PayInterest extends AbstractTransaction**: Pay Interest transaction. The bank will pay interest to saving account every day automatically
      * **Transfer extends AbstractTransaction**: Transfer transaction. For CaTransfer account.
         * **TransferIn extends Transfer**: Transfer In transaction. For Security account
         * **TransferOut extends Transfer**: Transfer Out transaction. For Security account
   * **StockTransaction implements Transaction**: Stock Transaction. For stock trade.
      * **BuyStock extends StockTransaction**: Buy Stock Transaction.
      * **SellStock extends StockTransaction**: Sell Stock Transaction.
  * ***order extends Transaction**: order interface. Transaction that need manager to solve*
      * **CollateralValuation implements order**: CollateralValuation. upload certificate and ask manager to valuate, using `apply()` method. Manager will solve the request, using `setPrice(double)` `setApprove()` `setReject()` method<br><br>
    

* ***ValCounter<T extends Valuable>**: ValCounter interface. Strategy Pattern, strategy interface*
* ***Valuable**: Valuable interface. Object that has value.*
* **ValuePool<T extends Valuable> extends HashMap<String, T>**: An hashmap that store valuable object. Context class in Strategy pattern. Can execute Strategy impl class. In our code the concrete class is anymous class<br><br>

* **Loan implements Valuable**:  Loan class. store balance and collateral.
* **Collateral implements Valuable**: Collateral class, store collateral value.
* **StockInfo**: StockInfo class, store stock name, current price and currency type.
   * **CustomerStock extends StockInfo implements Valuable**: stock that customer has. store in security account's stockPool.
   * **StockProfit extends StockInfo implements Valuable**: stock profit. store realized profit of one stock<br><br>

* **ConfigUtil**: ConfigUtil class. Get all properties from `config.properties` file.
* **JDBCUtil**: JDBCUtil class. Get mysql properties from `jdbc.properties` file and connect to mysql.
* **QueryUtil**: QueryUtil class. Help concat string for mysql query.
* **GuiUtil**: GuiUtil class, help find the frame of one component.<br><br>

* **MyFrame extends JFrame**: MyFrame class, set window position.
   * **CoreFrame extends MyFrame**: Core Frame class. The main frame.
      * **CustomerFrame extends CoreFrame**: The frame that shown after customer login
      * **BankerFrame extends CoreFrame**: The frame that shown after banker login
      * **LoginFrame extends CoreFrame**: The login frame for both customer and banker
      * **RegisterFrame extends CoreFrame**: The register frame for customer
   * **PopupFrame extends MyFrame**: Popup Frame class. 
      * **BuyStockFrame extends PopupFrame**: Buy Stock frame. Can choose 10, 20,50,100 percent.
      * **SellStockFrame extends PopupFrame**: Sell stock frame.Can choose 10, 20,50,100 percent.
      * **CreateAccountFrame extends PopupFrame**: Create account frame. Can choose pay the fee from another account or cash
      * **DepositFrame extends PopupFrame**: Deposit frame.
      * **WithdrawFrame extends PopupFrame**: Withdraw frame
      * **ExchangeFrame extends PopupFrame**: Exchange frame
      * **DepositTempFrame extends PopupFrame**: Deposit temp frame. Only for the deposit when creating account.
      * **RepaymentFrame extends PopupFrame**: Repayment frame
      * **TakeLoanFrame extends PopupFrame**: Take Loan fram. Can choose collateral and currency.
      * **TransferFrame extends PopupFrame**: Transfer frame. can transfer to other account or another customer.
      * **TransferInFrame extends PopupFrame**: Transfer In frame. only for security account. transfer in from saving account
      * **TransferOutFrame extends PopupFrame**: Transfer Out frame. only for security account. transfer out to saving account
      * **UploadCollateralFrame extends PopupFrame**: upload collateral frame. request collateral valuation and upload corresponding certificate
      * **MessageFrame extends PopupFrame**: Message frame. Show message after operation. success or fail.<br><br>

* **CustomerContentPanel extends JPanel**: CustomerContentPanel class. main panel of customer frame, which will change based on the operation.
   * **CollateralPanel extends CustomerContentPanel**: List Collateral info of the customer
   * **CollateralRequestPanel extends CustomerContentPanel**: List Collateral request info of the customer, including request status.
   * **CustomerHomepagePanel extends CustomerContentPanel**: HomePage panel. Show basic info of 4 accounts.
   * **TransactionHistoryPanel extends CustomerContentPanel**: Transaction history panel. List history transactions of one customer. 
   * **TablePanel extends CustomerContentPanel**: Abstract class. Show info in a table. Has a Currency Filter.
      * **LoanPanel extends TablePanel**: Loan info Table, show customer's loan info in the table
      * **StockListPanel extends TablePanel**: Available stock info Table, show stocks that be traded
      * **StockPositionPanel extends TablePanel**: Stock position info Table, show customer's current positions.
      * **StockProfitPanel extends TablePanel**: Stock profit info Table, show realized profit of the customer from each stock.
* **BankerHomepagePanel extends JPanel**: BankerHomePageOanel class. the main panel of banker frame, which will change based on the operation.
 
* **MyMenuButton extends JToggleButton**: MyMenuButton class. Implemented a toggle menu button.   
* **TableSetting**: set table design, the color of table.
* **TableColumn**: store the column name of different table.






# Design Pattern

1. Singleton : JDBCUtil is a singleton. There is only one Connection, and this connection is open once the program starts and will close if the program ends. 
   * Benefit:
       * make sure there is only one connection and easy to control.
       * easy to access database and make sure the connection last until the program ends.

2. DAO Pattern: 6 xxxDao class are Dao impl classes.
3. Strategy Pattern: *`ValCounter` interface* is the Strategy Interface. `ValPool<T>` class is the Context Class. The concrete classes are anonymous classes.
   * Benefit: 
     * sum up the target value of the objects in the HashMap based on the Strategy concrete class.
     * easy to sum up different attributes. easy to extend.
4. MVC Pattern: The whole part is MVC pattern. Controller layer execute and return the message to view layer to update the frame info.
   * Benefit:
     * loose coupling.
     * convenient to teamwork
     * high scalability and re-usability, easy to maintain



