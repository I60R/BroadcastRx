package i60r.broadcastrx;


import android.content.Context;
import android.content.IntentFilter;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;


/**
 * Created by 160R on 19.04.17.
 */

public class BroadcastRx {
    private static Context context = null;

    public static final void init(Context context) {
        BroadcastRx.context = context;
    }


    public static final Observable<OnBroadcast> global(final IntentFilter filter) {
        return Observable
                .create(new GlobalBroadcastOnSubscribe(filter, context));
    }

    public static final Observable<OnBroadcast> global(final IntentFilter... filters) {
        return Observable
                .fromArray(filters)
                .flatMap(new Function<IntentFilter, ObservableSource<OnBroadcast>>() {
                    @Override
                    public ObservableSource<OnBroadcast> apply(@NonNull IntentFilter filter) throws Exception {
                        return global(filter);
                    }
                });
    }

    public static final Observable<OnBroadcast> global(final String action) {
        return global(new IntentFilter(action));
    }

    public static final Observable<OnBroadcast> global(final String... actions) {
        return Observable
                .fromArray(actions)
                .reduce(new IntentFilter(), new BiFunction<IntentFilter, String, IntentFilter>() {

                    @Override
                    public IntentFilter apply(@NonNull IntentFilter filter, @NonNull String action) throws Exception {
                        filter.addAction(action);
                        return filter;
                    }
                })
                .flatMapObservable(new Function<IntentFilter, ObservableSource<? extends OnBroadcast>>() {
                    @Override
                    public ObservableSource<? extends OnBroadcast> apply(@NonNull IntentFilter filter) throws Exception {
                        return global(filter);
                    }
                });
    }


    public static final Observable<OnBroadcast> local(final IntentFilter filter) {
        return Observable
                .create(new LocalBroadcastOnSubscribe(filter, context));
    }

    public static final Observable<OnBroadcast> local(final IntentFilter... filters) {
        return Observable
                .fromArray(filters)
                .flatMap(new Function<IntentFilter, ObservableSource<OnBroadcast>>() {
                    @Override
                    public ObservableSource<OnBroadcast> apply(@NonNull IntentFilter filter) throws Exception {
                        return local(filter);
                    }
                });
    }

    public static final Observable<OnBroadcast> local(final String action) {
        return local(new IntentFilter(action));
    }

    public static final Observable<OnBroadcast> local(final String... actions) {
        return Observable
                .fromArray(actions)
                .reduce(new IntentFilter(), new BiFunction<IntentFilter, String, IntentFilter>() {

                    @Override
                    public IntentFilter apply(@NonNull IntentFilter filter, @NonNull String action) throws Exception {
                        filter.addAction(action);
                        return filter;
                    }
                })
                .flatMapObservable(new Function<IntentFilter, ObservableSource<? extends OnBroadcast>>() {
                    @Override
                    public ObservableSource<? extends OnBroadcast> apply(@NonNull IntentFilter filter) throws Exception {
                        return local(filter);
                    }
                });
    }
}

