package com.example.castle.castlelistview;

/**
 * Created by castle on 2015/12/8.
 */
public enum MainMsg {
    //from Main UI to Client Thread
    MUI_CLT_CMD_CONNECT,
    MUI_CLI_CMD_DISCONNECT,

    //from Client Thread to Main UI
    CLT_MUI_IND_CONNECT,
    CLI_MUI_IND_DISCONNECT,

}
