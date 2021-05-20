<h1 align = "center">My Fancy Bank</h1>

---

> # Description
>
> 
>
> Project Developers: **Yuqiu Lin** and **Xiongxin Zeng**.

---

---
## how to run
1. add `mysql-connector-java-8.0.20.jar` to dependencies
2. open mysql.Run following command.
    ```
   create database mybank;
   use mybank;
   source /filePath/mybank.sql;
   ```
3. change `username` and `password` to your mysql username and password in `jdbc.properties` file
4. play Main

## Classes of the project
* **Main**: Main class. Then entrance of the program.<br><br>
  
* **CustomerDao**: Customer DAO. Get Data from customer table.
* **AccountDao**: Account DAO. Get Data from account table.
* **StockDao**: Stock DAO. Get Data from stock table.
* **CollateralDao**: Collateral DAO. Get Data from collateral & collateralValuation table.
* **LoanDao**: Collateral DAO. Get Data from Loan table.
* **TransactionDao**: : Collateral DAO. Get Data from Transaction table.<br><br>

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
      * **Transfer extends AbstractTransaction**: Transfer transaction. For CaTransfer account.
         * **TransferIn extends Transfer**: Transfer In transaction. For Security account
         * **TransferOut extends Transfer**: Transfer Out transaction. For Security account
   * **StockTransaction implements Transaction**: Stock Transaction. For stock trade.
      * **BuyStock extends StockTransaction**: 
      * **SellStock extends StockTransaction**
  * ***order extends Transaction**: order interface. Transaction that need manager to solve*
      * **CollateralValuation implements order**: CollateralValuation. upload certificate and ask manager to valuate, using `apply()` method. Manager will solve the request, using `setPrice(double)` `setApprove()` `setReject()` method<br><br>
   

* ***ValCounter<T extends Valuable>**: ValCounter interface. Strategy Pattern, strategy interface*
* ***Valuable**: Valuable interface. Object that has value.*
* **ValuePool<T extends Valuable> extends HashMap<String, T>**<br><br>

* **Loan implements Valuable**: 
* **Collateral**: Collateral class, 
* **StockInfo**: StockInfo class, store stock name, current price and currency type.
   * **CustomerStock extends StockInfo implements Valuable**: stock that customer has. store in security account's stockPool.
   * **StockProfit extends StockInfo implements Valuable**: stock profit. store realized profit of one stock<br><br>

* **ConfigUtil**
* **StringUtil**
* **FileUtil**
* **QueryUtil**
* **StringUtil**
* **GuiUtil**
* **TableUtil**<br><br>

* **MyFrame extends JFrame**
   * **CoreFrame extends MyFrame**
      * **CustomerFrame extends CoreFrame**
      * **LoginFrame extends CoreFrame**
      * **RegisterFrame extends CoreFrame**
   * **PopupFrame extends MyFrame**
      * **BuyStockFrame extends PopupFrame**
      * **SellStockFrame extends PopupFrame**
      * **CreateAccountFrame extends PopupFrame**
      * **DepositFrame extends PopupFrame**
      * **WithdrawFrame extends PopupFrame**
      * **ExchangeFrame extends PopupFrame**
      * **DepositTempFrame extends PopupFrame**
      * **RepaymentFrame extends PopupFrame**
      * **TakeLoanFrame extends PopupFrame**
      * **TransferFrame extends PopupFrame**
      * **TransferInFrame extends PopupFrame**
      * **TransferOutFrame extends PopupFrame**
      * **UploadCollateralFrame extends PopupFrame**
      * **MessageFrame extends PopupFrame**<br><br>

* **CustomerContentPanel extends JPanel**
   * **CollateralPanel extends CustomerContentPanel**
   * **CollateralRequestPanel extends CustomerContentPanel**
   * **CustomerHomepagePanel extends CustomerContentPanel**
   * **CustomerLoanPanel extends CustomerContentPanel**
   * **TransactionHistoryPanel extends CustomerContentPanel**
   * **TablePanel extends CustomerContentPanel**
      * **LoanPanel extends TablePanel**
      * **StockListPanel extends TablePanel**
      * **StockPositionPanel extends TablePanel**
      * **StockProfitPanel extends TablePanel**
      * **StockProfitPanel extends TablePanel**
      * **StockProfitPanel extends TablePanel**<br><br>

* **MyMenuButton extends JToggleButton**
   






# Design Patten
1. MVC Pattern
2. Singleton Pattern: JDBCUtil is a singleton. There is only one Connection, and this connection is open once the program starts and will close if the program ends.
3. DAO Pattern: 6 xxxDao class




