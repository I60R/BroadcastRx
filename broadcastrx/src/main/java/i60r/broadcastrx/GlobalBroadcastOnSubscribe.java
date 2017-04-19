package i60r.broadcastrx;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Cancellable;

/**
 * Created by 160R on 19.04.17.
 */
class GlobalBroadcastOnSubscribe implements ObservableOnSubscribe<OnBroadcast> {
    private final IntentFilter filter;
    private final Context context;

    public GlobalBroadcastOnSubscribe(final IntentFilter filter, final Context context) {
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

            context.registerReceiver(receiver, filter);

            emitter.setCancellable(new Cancellable() {

                @Override
                public void cancel() throws Exception {
                    context.unregisterReceiver(receiver);
                }
            });
        }
    }
}
