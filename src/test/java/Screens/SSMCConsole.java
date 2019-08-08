package Screens;/*
 * © Copyright 2002-2015 Hewlett Packard Enterprise Development LP
 */





import java.util.concurrent.TimeUnit;

import static Helpers.Utility.sleep;


/**
 * This class includes all plug in objects shown within the StoreServ Management console web application.
 *
 * Created by stauffel on 12/10/13.
 */
public class SSMCConsole  {

    // - - - - - Class Attributes - - - - -

    private final String expectedTitle = "3PAR StoreServ";


    // - - - - - Constructor - - - - -

    public SSMCConsole () {
        return;
    }



    // - - - - - Plug In objects - - - - -

    /**
     *  This object allows you to interact with the UI objects declared within the Overview plug in.
     *
     *  @see                 com.hp.tpd.test.uipiano.screens.plugins.OverviewPlugIn
     */
    public final GeneralPlugIn general = GeneralPlugIn.INSTANCE;

    /**
     * This object allows you to interact with the UI objects declared within the File Services plug in.
     *
     *  @see                 com.hp.tpd.test.uipiano.screens.plugins.ProvisioningPlugIn
     */
    public final FilePersonaPlugIn filePersona = FilePersonaPlugIn.INSTANCE;

    /**
     * This object allows you to interact with the UI objects declared within the Storage Services plug in.
     *
     *  @see                 com.hp.tpd.test.ui.screens.HardwarePlugIn
     */
    public final BlockPersonaPlugIn blockPersona = BlockPersonaPlugIn.INSTANCE;

    /**
     * This object allows you to interact with the UI object declared within the System Reporter pulg in
     *
     * @see                 com.hp.tpd.test.ui.screens.SystemReporterPlugIn
     */
    public final SystemReporterPlugIn systemReporter = SystemReporterPlugIn.INSTANCE;

    /**
     * This object allows you to interact with the UI objects declared within the Hardware plug in.
     *
     *  @see                 com.hp.tpd.test.ui.screens.HardwarePlugIn
     */
    public final StorageSystemsPlugIn storageSystems = StorageSystemsPlugIn.INSTANCE;

    /**
     * This object allows you to interact with the UI objects declared within the Federation Manager plug in.
     *
     *  @see                 com.hp.tpd.test.ui.screens.FederationPlugIn
     */
    public final FederationPlugin federations = FederationPlugin.INSTANCE;

    /**
     * This object allows you to interact with the UI objects declared within the Security plug in.
     *
     *  @see                 com.hp.tpd.test.ui.screens.SecurityPlugIn
     */
    public final SecurityPlugIn security = SecurityPlugIn.INSTANCE;

    /**
     * This object allows you to interact with the UI objects declared within the Federation Manager plug in.
     *
     *  @see                 com.hp.tpd.test.ui.screens.FederationPlugIn
     */
    public final FederationsResource federation = FederationsResource.INSTANCE;

    /**
     * This object allows you to interact with the UI objects declared within the Federation Manager plug in.
     *
     *  @see                 com.hp.tpd.test.ui.screens.FederationPlugIn
     */
    public final PeerMotionsResource peermotion = PeerMotionsResource.INSTANCE;

    /**
     * This object allows you to interact with the UI objects declared within the VMware Storage Container plug in.
     *
     *  @see                 com.hp.tpd.test.ui.screens.vmware.StorageContainersResources
     */
    public final StorageContainersResource storageContainers = StorageContainersResource.INSTANCE;

    /**
     * This object allows you to interact with the UI objects declared within the VMware Virtual Machine plug in.
     *
     *  @see                 com.hp.tpd.test.ui.screens.vmware.StorageContainersResources
     */
    public final VirtualMachinesResource virtualMachines = VirtualMachinesResource.INSTANCE;

    /**
     * This object allows you to interact with the UI objects declared within the VMware Storage Container plug in.
     *
     *  @see                 com.hp.tpd.test.ui.screens.vmware.StorageContainersResources
     */
    public final StorageContainersResource storageContainers1 = StorageContainersResource.INSTANCE;

    /**
     * This object allows you to interact with the UI objects declared within the Remote Copy plug in.
     *
     *  @see                 com.hp.tpd.test.ui.screens.RemoteCopyPlugIn
     */
    public final ReplicationPlugIn replication = ReplicationPlugIn.INSTANCE;

    /**
     * This object allows you to interact with the UI objects declared within the Storage Optimizations plug in.
     *
     *  @see                 com.hp.tpd.test.ui.screens.storageoptimization
     */
    public final StorageOptimizationsPlugIn storageOptimizations = StorageOptimizationsPlugIn.INSTANCE;



    // - - - - - Misc objects - - - - -

    /**
     * This object allows you to interact with the UI objects declared within the Log In screen.
     *
     *  @see                 com.hp.tpd.test.ui.screens.LogInScreen
     */
    public final LogInScreen logInScreen = LogInScreen.INSTANCE;

    /**
     * This object allows you to interact with the UI objects declared within the Storage System Connection screen.
     * This screen is active when a user logs in as admin.  This screen allows the user to add (register) or remove
     * any 3PAR arrays to the SSMC console application.
     */
    public final AdministratorConsoleScreen adminConsoleScreen = AdministratorConsoleScreen.INSTANCE;

    /**
     * This is a placeholder for the Online Help Screen that is activated when the user clicks the "Online Help"
     * in the Help panel.
     */
    public final OnlineHelpScreen onlineHelpScreen = OnlineHelpScreen.INSTANCE;


    /**
     *  This object allows you to interact with the UI objects declared within the Overview plug in.
     *
     *  @see                 com.hp.tpd.test.uipiano.screens.dataprotection.DataProtectionPlugIn
     */
    public final DataProtectionPlugIn dataProtection = DataProtectionPlugIn.INSTANCE;

    // - - - - - Class Methods - - - - -

    /**
     * This method checks to see if the Help Pane is available.  The console is considered available if
     * the console is showing in the browser and ready for use.  If the console is available, the boolean value 'true'
     * will be returned.
     * <p>
     * If the console is not available, this method will keep checking on the console until:<br>
     * 1 - the console is available or<br>
     * 2 - the global ELEMENTTIMEOUT has been reached.
     * </p>
     * <p>
     * If the console is available before the global ELEMENTTIMEOUT is reached, this method returns a boolean value of 'true'.
     * </p>
     * <p>
     * If the console is not available, it may mean that the browser may have closed, or the browser is displaying a
     * different screen.  If this is the case, the boolean value 'false' would be returned.
     * </p>
     *
     * @return   a boolean 'true', if the console is showing in the browser, 'false' otherwise.
     */
    public boolean isAvailable() {
        return this.isAvailable(ELEMENTTIMEOUT);
    }


    /**
     * This method checks to see if the StoreServ Management Console is available.  The console is considered available if
     * the console is showing in the browser and ready for use.  If the console is available, the boolean value 'true'
     * will be returned.
     * <p>
     * If the console is not available, this method will keep checking on the console until:<br>
     * 1 - the console is available or<br>
     * 2 - the timeOutSeconds has been reached.
     * </p>
     * <p>
     * If the console is available before the timeOutSeconds is reached, this method returns a boolean value of 'true'.
     * </p>
     * <p>
     * If the console is not available, it may mean that the browser may have closed, or the browser is displaying a
     * different screen.  If this is the case, the boolean value 'false' would be returned.
     * </p>
     *
     * @return   a boolean 'true', if the console is showing in the browser, 'false' otherwise.
     */
    public boolean isAvailable(int timeOutSeconds) {
        // declaring local variables
        boolean returnValue = true;

        // waiting a second, because the web driver seems to be going too fast
        sleep(1, TimeUnit.SECONDS);

        if (!titleText.exists(timeOutSeconds)) {
            returnValue = false;
        } else {
            if (!titleText.getText().equals(expectedTitle)) {
                returnValue = false;
            }
        }

        return returnValue;
    }


    /**
     * This object is used to interact with the <b>connection error dialog</b> that could popup up while in the Dashboard screen.
     */
    public final ConnectionErrorDialog connectionErrorDialog = ConnectionErrorDialog.INSTANCE;

}
