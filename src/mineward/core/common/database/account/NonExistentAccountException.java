package mineward.core.common.database.account;

import mineward.core.common.utils.UtilConsoleLog;

public class NonExistentAccountException extends Exception {

    private static final long serialVersionUID = 1L;

    public NonExistentAccountException() {
        UtilConsoleLog.Log("NonExistentAccountException",
                "Could not find instance of supplied account");
    }

}
