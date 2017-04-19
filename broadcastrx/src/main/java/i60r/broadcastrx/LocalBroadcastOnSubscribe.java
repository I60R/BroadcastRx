package i60r.broadcastrx;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Cancellable;

/**
 * Created by 160R on 19.04.17.
 */
class LocalBroadcastOnSubscribe implements ObservableOnSubscribe<OnBroadcast> {
    private final IntentFilter filter;
    private final Context context;

    public LocalBroadcastOnSubscribe(IntentFilter filter, Context context) {
        this.filter = filter;
        this.context = context;
    }

    @Override
    public void subscribe(@NonNull final ObservableEmitter<OnBroadcast> emitter) throws Exception {
        if (context == null) {
            emitter.onError(new ExceptionInInitializerError("context must be set"));
        } else {
            final BroadcastReceiver receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    emitter.onNext(new OnBroadcast(context, intent));
                }
            };

            LocalBroadcastManager.getInstance(context).registerReceiver(receiver, filter);

            emitter.setCancellable(new Cancellable() {
                @Override
                public void cancel() throws Exception {
                    LocalBroadcastManager.getInstance(context).unregisterReceiver(receiver);
                }
            });
        }
    }
}
