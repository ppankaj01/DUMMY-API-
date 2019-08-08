/*
 * © Copyright 2002-2015 Hewlett Packard Enterprise Development LP
 */

package Screens;


/**
 * This class includes all panel objects found on the Dashboard plug in.
 *
 * generated class code
 */
public enum GeneralPlugIn {

    // - - - - - Class Attributes - - - - -
    INSTANCE;


    //- - - - - Panel Objects - - - - -
    /**
     * This object is used to interact with the <b>Dashboard Panel</b> on the Dashboard PlugIn.
     */
    public final DashboardPanel dashboard = DashboardPanel.INSTANCE;

    /**
     * This object is used to interact with the <b>Activity Panel</b> on the Dashboard PlugIn.
     */
    public final ActivityPanel activity = ActivityPanel.INSTANCE;

    /**
     * This object is used to interact with the <b>Schedules Panel</b> on the Dashboard PlugIn.
     */
    public final SchedulesPanel schedules = SchedulesPanel.INSTANCE;

    /**
     * This object is used to interact with the <b>Settings Panel</b> on the Dashboard PlugIn.
     */
    public final SettingsPanel settings = SettingsPanel.INSTANCE;

    /**
     * This object is used to interact with the <b>Contacts Panel</b> on the Dashboard PlugIn.
     */
    public ContactsPanel contactsPanel = ContactsPanel.INSTANCE;

}