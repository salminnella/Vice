package martell.com.vice.sync_adapter;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by anthony on 4/19/16.
 * The BOUND Service Instantiates the authenticator when started
 */
public class AuthenticatorService extends Service {

    // Instance field that stores the authenticator object
    private Authenticator mAuthenticator;
    @Override
    public void onCreate() {
        // Create a new authenticator object
        mAuthenticator = new Authenticator(this);
    }
    /*
     * When the system binds to this Service to make the RPC call
     * return the authenticator's IBinder.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
