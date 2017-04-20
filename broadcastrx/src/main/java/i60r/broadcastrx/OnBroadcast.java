package i60r.broadcastrx;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by 160R on 19.04.17.
 */
public class OnBroadcast {
    public final Context contxt;
    public final Intent  intent;
    public final String  action;
    public final String  sender;
    public final Bundle  extras;

    OnBroadcast(Context context, Intent intent) {
        this.contxt = context;
        this.intent = intent;
        this.action = intent.getAction();
        this.sender = intent.getPackage();
        this.extras = intent.getExtras();
    }
}
