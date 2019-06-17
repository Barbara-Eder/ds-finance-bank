/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finance.common;

/**
 *
 * @author Baris
 */
public class FinanceException extends java.lang.Exception{
    
    public FinanceException() {
  }
    
    
    public FinanceException(Throwable cause) {
    super(cause);
  }
    public FinanceException(String message) {
        super(message);
    }
}
