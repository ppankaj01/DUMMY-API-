/*
 * © Copyright 2002-2017 Hewlett Packard Enterprise Development LP
 */

package Screens;

import Elements.Text;

import com.hp.piano.test.ui.elements.*;
import com.hp.piano.test.ui.elements.By;
import Elements.By;
import com.hp.tpd.test.uipiano.screens.BasePanel;
import com.hp.tpd.test.uipiano.screens.general.dashboard.Dialogs.AddSystemsDialog;


/**
 * This class defines all objects found on the SSMC Dashboard.
 */
public class DashboardPanel extends BasePanel {

    // - - - - - Class Attributes - - - - -
    public static final DashboardPanel INSTANCE = new DashboardPanel();

    /**
     * This object is used to interact with the <b>Add Panels dialog</b> when using the Action menu item 'Add Panels'.
     */
    public final AddPanelsDialog addPanelsDialog = AddPanelsDialog.INSTANCE;

    /**
     * This object is used to interact with the <b>Delete action</b> when using the Action menu item 'Delete'.
     */
    public final DeleteDashboardPanel deleteDashboardPanel = DeleteDashboardPanel.INSTANCE;

    /**
     * This object is used to interact with the <b>Edit top host bandwidth panel</b> when clicking on edit icon.
     */
    public final EditTopHostBandwidthPanel editTopHostBandwidthPanel = EditTopHostBandwidthPanel.INSTANCE;

    /**
     * This object is used to interact with the <b>Edit top host port utilization panel</b> when clicking on edit icon.
     */
    public final EditTopHostPortutilizationPanel editTopHostPortUtilizationPanel = EditTopHostPortutilizationPanel.INSTANCE;


    /**
     * This object is used to interact with the <b>Edit performance panel</b> when clicking on edit icon.
     */
    public final EditSystemPerformancePanel editSystemPerformancePanel = EditSystemPerformancePanel.INSTANCE;

    /**
     * This object is used to interact with the <b>Edit activity panel</b> when clicking on edit icon.
     */
    public final EditActivityPanel editActivityPanel = EditActivityPanel.INSTANCE;


    /**
     * This object is used to interact with the <b>Edit Historiocal Capacity panel</b> when clicking on edit icon.
     */
    public final EditHistoricalCapacityPanel editHistoricalCapacityPanel = EditHistoricalCapacityPanel.INSTANCE;

    /**
     * This object is used to interact with the <b>Edit system dialog</b> when clicking on edit icon.
     */
    public final EditSystemDialogPanel editSystemDialogPanel = EditSystemDialogPanel.INSTANCE;

    /**
     * This object is used to interact with the <b>Edit Capacity efficiency dialog</b> when using edit icon.
     */
    public final EditCapacityEfficiencyDialog editCapacityEfficiencyDialog = EditCapacityEfficiencyDialog.INSTANCE;

     /**
     * This object is used to interact with the <b>Edit Capacity efficiency dialog</b> when using edit icon.
     */
    public final EditDeviceTypeCapacityPanel editDeviceTypeCapacityDialog = EditDeviceTypeCapacityPanel.INSTANCE;

    /**
     * This object is used to interact with the <b>Edit Performance dialog</b> when using edit icon.
     */
    public final EditDashboardPerformancePanel editDashboardPerformancePanel = EditDashboardPerformancePanel.INSTANCE;

    /**
     * This object is used to interact with the <b>Edit Raw Capacity</b> when using edit icon.
     */
    public final EditRawCapacityPanel editRawCapacityPanel = EditRawCapacityPanel.INSTANCE;

    /**
     * This object is used to interact with the <b>Edit Storage system</b> when using edit icon.
     */
    public final EditStorageSystemPanel editStorageSystemPanel = EditStorageSystemPanel.INSTANCE;

    /**
     * This object is used to interact with the <b>Edit Device type capacity panel</b> when using edit icon.
     */
    public final EditDeviceTypeCapacityPanel editDeviceTypeCapacityPanel = EditDeviceTypeCapacityPanel.INSTANCE;


    /**
     * This object is used to interact with the <b>Edit total capacity panel dialog</b> when clicking on edit icon.
     */
    public final TotalCapacityPanelEditDialog editTotalCapacityPanelDialog = TotalCapacityPanelEditDialog.INSTANCE;


    /**
     * This object is used to interact with the <b>Edit Top Volume Bandwidth Panel dialog</b> when clicking on edit icon.
     */
    public final EditTopVolumeBandwidthPanel editTopVolumeBandwidthPanel = EditTopVolumeBandwidthPanel.INSTANCE;

    /**
     * This object is used to interact with the <b>Edit allocated capacity panel dialog</b> when clicking on edit icon.
     */
    public final AllocatedCapacityPanelEditDialog editAllocatedCapacityPanelDialog = AllocatedCapacityPanelEditDialog.INSTANCE;


    /**
     * This object is used to interact with the <b>Edit common actions and views panel</b> when using edit icon.
     */
    public final EditCommonActionsAndViewsPanel editCommonActionsAndViewsPanel = EditCommonActionsAndViewsPanel.INSTANCE;

    /**
     * This object is used to interact with the <b>create Dashboard Panel </b> when using gear icon.
     */
    public  final CreateDashboardPanel createDashboardPanel = CreateDashboardPanel.INSTANCE;

    /**
     * This object is used to interact with the <b>Edit Dashboard Panel </b> when using gear icon.
     */
    public  final EditAllSysytemsDialog editAllSysytemsDialog = EditAllSysytemsDialog.INSTANCE;
    /**
     * This object is used to interact with the <b>Edit inside Edit  Dashboard Panel </b> when we click on Edit button
     */
    public  final EditPanelsAndSystemsDialog  editPanelsAndSystemsDialog = EditPanelsAndSystemsDialog.INSTANCE;
    /**
     * This object is used to interact with the <b>create copy of Dashboard Panel </b> when using gear icon.
     */
    public  final CreateCopyOfDashboardPanel  createCopyOfDashboardPanel = CreateCopyOfDashboardPanel.INSTANCE;

    /**
     * This object is used to interact with the <b>Edit system summary panel</b> when clicking on edit icon.
     */
    public final EditSystemSummaryPanel editSystemSummaryPanel = EditSystemSummaryPanel.INSTANCE;

    /**
     * This object is used to interact with the <b>Manage dialog</b>
     */
    public final ManageDashboardDialog manageDashboardDialog = ManageDashboardDialog.INSTANCE;

    /**
     * This object is used to interact with the <b>Edit VirtualVolume Protection Panel</b> when clicking on edit icon.
     */
    public final EditVirtualVolumeProtectionPanel editVirtualVolumeProtectionPanel = EditVirtualVolumeProtectionPanel.INSTANCE;
    /**
     * This object is used to interact with the <b>Edit  Protection Job Panel</b> when clicking on edit icon.
     */
    public final EditProtectionJobPanel editProtectionJobPanel = EditProtectionJobPanel.INSTANCE;

    /**
     * This object is used to interact with the <b>Edit  Block Persona Status Panel</b> when clicking on edit icon.
     */
    public final EditBlockPersonaStatusPanel editBlockPersonaStatusPanel = EditBlockPersonaStatusPanel.INSTANCE;

    /**
     * This object is used to interact with the <b>Common Actions and Views panel virtual volume link </b> shown on the Dashboard.
     */

    public final TextLink commonActionsAndViewsVirtualVolumeLink = new TextLink(By.cssSelector("a[id^='ssmc-dashboard-action-virtual-volumes']"));

    /**
     * This object is used to interact with the <b>Common Actions and Views panel file share link </b> shown on the Dashboard.
     */

    public final TextLink commonActionsAndViewsFileShareLink = new TextLink(By.cssSelector("a[id^='ssmc-dashboard-action-file-shares']"));


    /**
     * This object is used to interact with the <b>Common Actions and Views panel Host link </b> shown on the Dashboard.
     */

    public final TextLink commonActionsAndViewsHostLink = new TextLink(By.cssSelector("a[id^='ssmc-dashboard-action-hosts']"));

    /**
     * This object is used to interact with the <b>Common Actions and Views panel virtual volumes link in Views Section </b> shown on the Dashboard.
     */

    public final TextLink commonActionsAndViewsVirtualVolumesLink = new TextLink(By.cssSelector(".ssmc-dashboard-form-item > a[href='#/virtual-volumes/show']"));


    /**
     * This object is used to interact with the <b>Common Actions and Views panel Hosts link in views Section </b> shown on the Dashboard.
     */

    public final TextLink commonActionsAndViewsHostsLink = new TextLink(By.cssSelector(".ssmc-dashboard-form-item > a[href='#/hosts/show']"));



    /**
     * This object is used to interact with the <b>Common Actions and Views panel Host link in views section</b> shown on the Dashboard.
     */

    public final TextLink commonActionsAndViewsHostsSetsLink = new TextLink(By.cssSelector(".ssmc-dashboard-form-item > a[href='#/host-sets/show']"));


    /**
     * This object is used to interact with the <b>Common Actions and Views panel reports link </b> shown on the Dashboard.
     */

    public final TextLink commonActionsAndViewsReportsLink = new TextLink(By.cssSelector(".ssmc-dashboard-form-item > a[href='#/sr-reports/show']"));



    /**
     * This object is used to interact with the <b>Total CapacityPanel</b> label shown on the Dashboard.
     */
    public final TextLink totalCapacityPanel = new TextLink(By.cssSelector("div[id^='DashboardTotalCapacityPanel'] > div > header > h2"));

    /**
     * This object is used to interact with the <b>Total CapacityPanel Allocated label</b> label shown on the Dashboard.
     */
    public final Text totalCapacityAllocated = new Text(By.cssSelector("#DashboardTotalCapacityPanel-donut-alloc"));

    /**
     * This object is used to interact with the <b>Total CapacityPanel Free label</b> label shown on the Dashboard.
     */
    public final Text totalCapacityFreelabel = new Text(By.cssSelector("#DashboardTotalCapacityPanel-donut-free"));

    /**
     * This object is used to interact with the <b>Total CapacityPanel Other label</b> label shown on the Dashboard.
     */
    public final Text totalCapacityOtherlabel = new Text(By.cssSelector("#DashboardTotalCapacityPanel-donut-other"));
    /**
     * This object is used to interact with the <b>Total CapacityPanel Total label</b> label shown on the Dashboard.
     */
    public final Text totalCapacityTotallabel = new Text(By.cssSelector("#DashboardTotalCapacityPanel-donut-total"));

    /**
     * This object is used to interact with the <b>Total Capacity Edit Button</b> shown in Dash Board
     */

    public final Text totalCapacityPanelEditButton = new Text(By.cssSelector("#DashboardTotalCapacityPanel > div > header > div > div.hp-icon.hp-row-controls.hp-edit"));


    /**
     * This object is used to interact with the <b>Edit link on activity panel </b> if system is hetrogenius in Dash Board
     */

    public final TextLink activityEditLink = new TextLink(By.cssSelector("div[id^='DashboardActivityPanel'] > div > div.hp-page-splash > div > a > span"));



    /**
     * This object is used to interact with the <b>allocatedCapacityPanel</b> label shown on the Dashboard.
     */
    public final TextLink allocatedCapacityPanel = new TextLink(By.cssSelector("#DashboardAllocCapacityPanel> div > header >h2 >a"));

    /**
     * This object is used to interact with the <b>Allocated Capacity Panel Block label</b> label shown on the Dashboard.
     */
    public final Text allocatedCapacityBlocklabel = new Text(By.cssSelector("#DashboardAllocCapacityPanel-donut-block"));

    /**
     * This object is used to interact with the <b>Allocated Capacity Panel File label</b> label shown on the Dashboard.
     */
    public final Text allocatedCapacityFilelabel = new Text(By.cssSelector("#DashboardAllocCapacityPanel-donut-file"));

    /**
     * This object is used to interact with the <b>Allocated Capacity Panel System label</b> label shown on the Dashboard.
     */
    public final Text allocatedCapacitySystemlabel = new Text(By.cssSelector("#DashboardAllocCapacityPanel-donut-sys"));
    /**
     * This object is used to interact with the <b>Allocated Capacity Panel Total label</b> label shown on the Dashboard.
     */
    public final Text allocatedCapacityTotallabel = new Text(By.cssSelector("#DashboardAllocCapacityPanel-donut-tot"));


    /**
     * This object is used to interact with the <b>Total Capacity Edit Button</b> shown in Dash Board
     */

    public final Text allocatedCapacityPanelEditButton = new Text(By.cssSelector("#DashboardAllocCapacityPanel > div > header > div > div.hp-icon.hp-row-controls.hp-edit"));

    /**
     **
     * This object is used to interact with the <b>Single system total capacity label</b>  shown on the Dashboard.
     */
    public final Text totalCapacityPanelLabel = new Text(By.cssSelector("div[id^='DashboardTotalCapacityPanel'] > div > header > h2 span.hp-name"));


    /**
     **
     * This object is used to interact with the <b>Single system total capacity label</b>  shown on the Dashboard.
     */
    public final TextLink totalCapacityPanel_EachSystemLink = new TextLink(By.cssSelector("div[id^='DashboardTotalCapacityPanel'] > div > header > h2 span.hp-name"));

    /**
     **
     * This object is used to interact with the <b>Single system total capacity label</b>  shown on the Dashboard.
     */
    public final TextLink totalCapacityPanelLabelLink = new TextLink(By.cssSelector("div[id^='DashboardTotalCapacityPanel'] > div > header > h2 span.hp-name"));


    /**
     * This object is used to interact with the <b>Add system dialog</b> when clicking on add systems button.
     */
    public final AddSystemsDialog addSystemsDialog = AddSystemsDialog.INSTANCE;

    // - - - - - Constructor - - - - -
    private DashboardPanel() {
        // using super constructor, with the menu item for this class, and the id for this panel
        super("Dashboard", By.id("ssmc-customdashboard-page"));
        this.hasTableCount = false;
    }

    /**
     * This object is used to interact with the <b>Storage Systems</b> label shown on the Dashboard.
     */
    public final TextLink storageSystemsLabel = new TextLink(By.cssSelector("[id^=DashboardSystemsPanel] > div > header > h2 > a > span.hp-name"));

    /**
     * This object is used to interact with the <b>Storage Systems</b> donut grid shown on the Dashboard.
     */
    public final DonutGrid storageSystemsGrid = new DonutGrid(By.id("ssmc-dashboard-storagesystems"));

    /**
     * This object is used to interact with the <b>Performance</b> label shown on the Dashboard.
     */
    public final TextLink performanceLabel = new TextLink(By.cssSelector("[id^='DashboardPerformancePanel'] > div > header > h2 > a > span"));

    /**
     * This object is used to interact with the <b>Performance</b> donut grid shown on the Dashboard.
     */
    public final DonutGrid performanceGrid = new DonutGrid(By.id("ssmc-dashboard-performance"));

    /**
     * This object is used to interact with the <b>Total Capacity</b> label shown on the Dashboard.
     */
    public final TextLink totalCapacityLabel = new TextLink(By.cssSelector("#DashboardTotalCapacityPanel > div > header > h2 > a"));

    /**
     *
     */
    public final Table totalCapacityDetail = new Table(By.cssSelector("#DashboardTotalCapacityPanel-donut-tableId > tbody"));

    /**
     * This object is used to interact with the <b>Total Capacity</b> donut grid shown on the Dashboard.
     */
    public final DonutGridWithLegends totalCapacityGrid = new DonutGridWithLegends(By.cssSelector("[id^=DashboardTotalCapacityPanel]"));

    /**
     * This object is used to interact with the <b>Capacity Efficiency</b> label shown on the Dashboard.
     */
    public final TextLink capacityEfficiencyLabel = new TextLink(By.cssSelector("div[id^='DashboardCapacityEfficiencyPanel']> div > header > h2 > a > span"));


    /**
     * This object is used to interact with the <b>Capacity Efficiency Panel Savings label</b> label shown on the Dashboard.
     */
    public final Text capacityEfficiencySavingslabel = new Text(By.cssSelector("#DashboardCapacityEfficiencyPanel-donut-sav"));

    /**
     * This object is used to interact with the <b>Capacity Efficiency Panel Used label</b> label shown on the Dashboard.
     */
    public final Text capacityEfficiencyUsedlabel = new Text(By.cssSelector("#DashboardCapacityEfficiencyPanel-donut-rsrvd"));

    /**
     * This object is used to interact with the <b>Capacity Efficiency Panel Usable label</b> label shown on the Dashboard.
     */
    public final Text capacityEfficiencyUsablelabel = new Text(By.cssSelector("#DashboardCapacityEfficiencyPanel-donut-exp"));
    /**
     * This object is used to interact with the <b>Capacity Efficiency</b> donut grid shown on the Dashboard.
     */
    public final DonutGridWithLegends capacityEfficiencyGrid = new DonutGridWithLegends(By.cssSelector("[id^='DashboardCapacityEfficiencyPanel'][class$='hp-donut']"));

    /**
     * This object is used to interact with the <b>Activity</b> label shown on the Dashboard.
     */
    public final TextLink activityLabel = new TextLink(By.cssSelector("div[id^='DashboardActivityPanel'] > div > header > h2 span.hp-name"));

    /**
     * This object is used to interact with the <b>Activity Delete Icon</b> label shown on the Dashboard.
     */
    public final TextLink activityDeleteIcon = new TextLink(By.cssSelector("div[id^='DashboardActivityPanel'] > div > header > div > div.hp-controls > div.hp-close"));


    /**
     * This object is used to interact with the <b>Activity</b> label shown on the Dashboard.
     */
    public final TextLink activityEditIcon = new TextLink(By.cssSelector("div[id^='DashboardActivityPanel'] > div > header > div > div.hp-icon.hp-row-controls.hp-edit"));

    /**
     * This object is used to interact with the <b>Overall Compaction</b> label shown under the <b>Capacity
     * Efficiency</b> section of the Dashboard.
     */
    public final Text overallCompactionLabel = new Text(By.cssSelector("div[data-localize='dashboard.compaction']"));

    /**
     * This object is used to interact with the <b>Overall Compaction</b> value shown under the <b>Capcity
     * Efficiency</b> section of the Dashboard.
     */
    public final Text overallCompactionValue = new Text(By.id("ssmc-dashboard-overallCompaction"));

    /**
     * This object is used to interact with the <b>Device Type Capacity</b> label shown on the Dashboard.
     */
    public final TextLink deviceTypeCapacityLabel = new TextLink(By.cssSelector("div[id^='DashboardDeviceTypeCapacityPanel']> div > header > h2 > a > span"));
    /**
     * This object is used to interact with the <b>Device Type Capacity</b> donut grid shown on the Dashboard.
     */
    public final DonutGrid deviceTypeCapacityGrid = new DonutGrid(By.cssSelector("div[id^='DashboardDeviceTypeCapacityPanel']"));

    /**
     * This object is used to interact with the <b>Device Type Capacity Panel SSD label</b> label shown on the Dashboard.
     */
    public final Text deviceTypeCapacitySSDlabel = new Text(By.cssSelector("#ssmc-dashboard-ssdMeter-value"));

    /**
     * This object is used to interact with the <b>Device Type Capacity Panel FC label</b> label shown on the Dashboard.
     */
    public final Text deviceTypeCapacityFClabel = new Text(By.cssSelector("#ssmc-dashboard-fcMeter-value"));

    /**
     * This object is used to interact with the <b>Device Type Capacity Panel NL label</b> label shown on the Dashboard.
     */
    public final Text deviceTypeCapacityNLabel = new Text(By.cssSelector("#ssmc-dashboard-nlMeter-value"));


    /**
     * This object is used to interact with the <b>Device Type Capacity Table</b> in the {@link #deviceTypeCapacityGrid Device Type Capacity grid}.
     */
    public final Table deviceTypeCapacityTable = new Table(By.cssSelector("#ssmc-dashboard-devicetypecapacity table table"));

    /**
     * This object is used to interact with the <b>Allocated Capacity</b> label shown on the Dashboard.
     */
    public final TextLink allocatedCapacityLabel = new TextLink(By.cssSelector("div[id^='DashboardAllocCapacityPanel'] > div > header > h2 > a"));

    /**
     * This object is used to interact with the <b>Allocated Capacity</b> donut grid shown on the Dashboard.
     */
    public final DonutGridWithLegends allocatedCapacityGrid = new DonutGridWithLegends(By.cssSelector("[id^=DashboardAllocCapacityPanel]"));

    /**
     * This object is used to interact with the <b>Historical Capacity</b> label shown on the Dashboard.
     */
    public final TextLink historicalCapacityLabel = new TextLink(By.cssSelector("div[id^='DashboardHistoricChartPanel'] a span[class='hp-name']"));

    /**
     * This object is used to interact with the <b>Historical Capacity Delete Icon</b> label shown on the Dashboard.
     */
    public final TextLink historicalCapacityDeleteIcon = new TextLink(By.cssSelector("[id^=DashboardHistoricChartPanel] > div > header > div > div.hp-controls > div"));

    /**
     * This object is used to interact with the <b>Historical Capacity</b> chart shown on the Dashboard.
     */
    public final DashboardChart historicalCapacityChart = new DashboardChart(By.id("ssmc-dashboard-history-chart"), By.id("dashboard-history-legend"));

    /**
     * This object is used to interact with the <b>Activity Panel</b> on the Dashboard.
     */
    public final DonutGridWithLegends activityPanel = new DonutGridWithLegends(By.xpath("//div[contains(@id,'DashboardActivityPanel')]"));
    /**
     * This object is used to interact with the <b>Edit Icon</b> label shown on the Dashboard.
     */
    public final IconButton editIcon = new IconButton(By.id("ssmc-dashboard-edit-link"));

    /**
     * This object is used to interact with the <b>Activity Panel</b> on the Dashboard.
     */
    public final TextLink addPanelsTextLink = new TextLink(By.xpath("//div[@id='ssmc-dashboard-edit-page']/nav/a"));


    /**
     * This object is used to interact with the <b>Device Type Capacity</b> donut grid shown on the Dashboard.
     */
    public final DonutGrid addPanelsWindow = new DonutGrid(By.id("ssmc-dashboard-edit-addpanels-page"));

    /**
     * This object is used to interact with the <b>Close button</b>.
     */
    public final Button okButton = new Button(By.id("ssmc-dashboard-edit-ok"), "OK");

    /**
     * This object is used to interact with the <b>Close button</b>.
     */
    public final Button addButton = new Button(By.id("ssmc-dashboard-addpanels-add"), "Add");

    /**
     * This object is used to interact with the <b>Alert Link</b> shown in ActivityPanel in Dash Board
     */
    public final TextLink alertLink = new TextLink(By.id("ssmc-dashboard-activity-alerts"));

    /**
     * This object is used to interact with the <b>Alert Link</b> shown in ActivityPanel in Dash Board
     */
    public final TextLink taskLink = new TextLink(By.id("ssmc-dashboard-activity-tasks"));

    /**
     * This object is used to interact with the <b>CriticalAlerts Count</b> shown in ActivityPanel in Dash Board
     */
    public final TextLink criticalAlertsCount = new TextLink(By.cssSelector("li[data-name='criticalAlerts'] > a"));

    /**
     * This object is used to interact with the <b>warningAlertsCount</b> shown in ActivityPanel in Dash Board
     */
    public final TextLink warningAlertsCount = new TextLink(By.cssSelector("li[data-name='warningAlerts'] > a"));

    /**
     * This object is used to interact with the <b>CcriticalTaskCount</b> shown in ActivityPanel in Dash Board
     */
    public final TextLink criticalTaskCount = new TextLink(By.cssSelector("li[data-name='criticalTasks'] > a"));

    /**
     * This object is used to interact with the <b>warningTaskCount</b> shown in ActivityPanel in Dash Board
     */
    public final TextLink warningTaskCount = new TextLink(By.cssSelector("li[data-name='warningTasks'] > a"));

    /**
     * This object is used to interact with the <b>Threshold criticalAlerts Count</b> shown in ActivityPanel in Dash Board
     */
    public final TextLink thresholdCriticalAlertsCount = new TextLink(By.cssSelector("li[data-name='criticalThresholdAlerts'] > a"));


    /**
     * This object is used to interact with the <b>Threshold warningAlerts Count</b> shown in ActivityPanel in Dash Board
     */
    public final TextLink thresholdWarningAlertsCount = new TextLink(By.cssSelector("li[data-name='warningThresholdAlerts'] > a"));

    /**
     * This object is used to interact with the <b>Dashboard Actions</b> shown in Dash Board
     */
    public final Menu gearIcon = new Menu(By.id("hp-dashboard-actions"));

    /**
     * This object is used to interact with the <b>Storage system delete Icon</b> shown in Dash Board
     */

    public final Text storageSystemDeleteIcon = new Text(By.cssSelector("[id^='DashboardSystemsPanel']> div > header > div > div.hp-controls > div"));


    /**
     * This object is used to interact with the <b>Dashboard Actions</b> shown in Dash Board
     */

    public final TextLink systemEditIcon = new TextLink(By.cssSelector("[id^='DashboardSystemsPanel']> div > header > div > div.hp-icon.hp-row-controls.hp-edit"));

    /**
     * This object is used to interact with the <b>Daily top host bandwidth</b> label shown on the Dashboard.
     */
    public final TextLink dailyTopHostBandwidthLabel = new TextLink(By.cssSelector("div[id^='DashboardBandwidthPanel'] > div > header > h2 > span"));

    /**
     * This object is used to interact with the <b>Daily top host port utilization</b> label shown on the Dashboard.
     */
    public final TextLink dailyTopHostPortUtilizationLabel = new TextLink(By.cssSelector("div[id^='DashboardPortPanel'] > div > header > h2 > span"));

    /**
     * This object is used to interact with the <b>Daily top host port utilization</b> of edit top host bandwidth.
     */
    public final TextLink dailyTopHostPortUtilizationEdit = new TextLink(By.cssSelector("div[id^='DashboardPortPanel'] > div > header > div > div.hp-icon.hp-row-controls.hp-edit"));

    /**
     * This object is used to interact with the <b>Daily top host bandwidth</b> of edit top host bandwidth.
     */
    public final TextLink dailyTopHostBandwidthEdit = new TextLink(By.cssSelector("div[id^='DashboardBandwidthPanel'] > div > header > div > div.hp-icon.hp-row-controls.hp-edit"));

    /**
     * This object is used to interact with the <b>Daily top host bandwidth</b> delete icon shown on the Dashboard.
     */
    public final TextLink dailyTopHostBandwidthDeleteIcon = new TextLink(By.cssSelector("div[id^='DashboardBandwidthPanel'] > div > header > div > div.hp-controls > div.hp-close"));

    /**
     * This object is used to interact with the <b>Daily top host port utilization</b> delete icon shown on the Dashboard.
     */
    public final TextLink dailyTopHostPortUtilizationDeleteIcon = new TextLink(By.cssSelector("div[id^='DashboardPortPanel'] > div > header > div > div.hp-controls > div.hp-close"));

    /**
     * This object is used to interact with the <b>Daily top host bandwidth</b> data shown on the Dashboard.
     */
    public final Text noActivityHost = new Text(By.cssSelector("div[id^='DashboardBandwidthPanel']> div > div.hp-page-splash > div"));

    /**
     * This object is used to interact with the <b>Daily top host bandwidth</b> data shown on the Dashboard.
     */


    public final Text readText = new Text(By.cssSelector("div[id^='ssmc-oriondashboard-topHostBandwidth'] > div.hp-legend  span.hp-color.hp-color-blue + span"));


    /**
     * This object is used to interact with the <b>Daily top host bandwidth</b> data shown on the Dashboard.
     */


    public final Text writeText = new Text(By.cssSelector("div[id^='ssmc-oriondashboard-topHostBandwidth'] > div.hp-legend span.hp-color.hp-color-subduedpurple + span"));


    /**
     * This object is used to interact with the <b>System performance</b> label shown on the Dashboard.
     */


    public final TextLink systemPerformanceLabel = new TextLink(By.cssSelector("div[id^='DashboardSinglePerformancePanel'] > div > header > h2 span.hp-name"));

    /**
     * This object is used to interact with the <b>System performance Delete Icon</b>  shown on the Dashboard.
     */


    public final TextLink systemPerformanceDeleteIcon = new TextLink(By.cssSelector("div[id^='DashboardSinglePerformancePanel'] > div > header > div > div.hp-controls > div.hp-close"));

    /**
     * This object is used to interact with the <b>System performance</b> data shown on the Dashboard.
     */

    public final Text systemPerformanceDetails = new Text(By.id("ssmc-dashboard-performance-span-msg"));

    /**
     * This object is used to interact with the <b>System performance </b> label shown on the Dashboard.
     */
    public final TextLink systemPerformanceEditLink = new TextLink(By.cssSelector("div[id^='DashboardSinglePerformancePanel'] > div > div.hp-page-splash > div > a > span"));

    /**
     * This object is used to interact with the <b>Select System dropdown</b> on the Dashboard.
     */
    public final SelectorMenu systemSelectorFilterDashboard = new SelectorMenu(By.cssSelector("#ssmc-dashboard-view-select"));

    /**
     * This object is used to interact with the <b>Storage Systems</b> label shown on the Dashboard.
     */
    public final TextLink singleSystemStorageSystemsLabel = new TextLink(By.cssSelector("[id^='DashboardSingleSystemPanel']> div > header > h2 > a"));
    /**
     * This object is used to interact with the <b>Performance</b> label shown on the Dashboard.
     */
    public final TextLink singleSystemPerformanceLabel = new TextLink(By.cssSelector("[id^='DashboardSinglePerformancePanel']> div > header > h2 > a"));
    /**
     * This object is used to interact with the <b>Raw Capacity panel</b> of add panels dialog.
     */

    public final Text singleSystemRawCapacityPanelText = new Text(By.cssSelector("#DashboardRawCapacityPanel > div > header > h2 > a"));
    /**
     * This object is used to interact with the <b>Activity panel</b> of add panels dialog.
     */

    public final Text singleSystemActivityPanelText = new Text(By.cssSelector("#DashboardActivityPanel > div > header > h2 > a"));
    /**
     * This object is used to interact with the <b>Common Actions and Views panel</b> of add panels dialog.
     */

    public final Text singleSystemCommonActionsAndViewsPanelText = new Text(By.cssSelector("#DashboardActionsPanel > div > header > h2 > span"));
    /**
     * This object is used to interact with the <b>Single system Daily Top Host Bandwidth panel</b> of add panels dialog.
     */

    public final Text singleSystemDailyTopHostBandwidthPanelText = new Text(By.cssSelector("#DashboardBandwidthPanel > div > header > h2 > span"));

    /**
     * This object is used to interact with the <b>Single System system panel</b> of add panels dialog.
     */
    public final Text singleSystemPanelSystemNameText = new Text(By.cssSelector("div[id='ssmc-dashboard-single-system-panel'] > form > ol span.hp-summary-status + span"));

    /**
     * This object is used to interact with the <b>Model Text Single System system panel</b> of Dashboard.
     */
    public final Text singleSystemPanelSystemModelText = new Text(By.id("ssmc-dashboard-single-system-model"));

    /**
     * This object is used to interact with the <b>Sr No of Single System system panel</b> of Dashboard.
     */
    public final Text singleSystemPanelSystemSrNoText = new Text(By.id("ssmc-dashboard-single-system-serialNumber"));

    /**
     * This object is used to interact with the <b>OS version of Single System system panel</b> of Dashboard.
     */
    public final Text singleSystemPanelSystemOSVersionText = new Text(By.id("ssmc-dashboard-single-system-osversion"));

    /**
     * This object is used to interact with the <b>Details of Single System system panel</b> of Dashboard.
     */
    public final Table singleSystemPanelDetails = new Table(By.cssSelector("div[id='ssmc-dashboard-single-system-details'] > div > table"));

    /**
     * This object is used to interact with the <b>No Details of Single System system panel</b> of Dashboard.
     */
    public final Text singleSystemPanelSystemNoDetailsText = new Text(By.cssSelector("div#ssmc-dashboard-single-system-details > div > div.hp-unset"));

    /**
     * This object is used to interact with the <b> State of Single System system panel</b> of Dashboard.
     */
    public final Text singleSystemPanelSystemStateText = new Text(By.id("ssmc-dashboard-single-system-state"));


    /**
     * This object is used to interact with the <b>Delete Textlink</b> of System in single system dashboard view.
     */
    public final TextLink deleteSingleSystem = new TextLink(By.cssSelector("div[id^='DashboardSingleSystemPanel'] > div > header > div > div.hp-controls > div"));

    /**
    * This object is used to interact with the <b>Edit Icon</b> of historical capacity shown on the Dashboard.
    */
    public final IconButton editHistoricalCapacity = new IconButton(By.cssSelector("[id^=DashboardHistoricChartPanel] > div > header > div > div.hp-icon.hp-row-controls.hp-edit"));


    /**
     * This object is used to interact with the <b>Historical capacity label</b>shown on the Single system dashboard.
     */
    public final TextLink singleSystemHistoricalCapacityLabel = new TextLink(By.cssSelector("div[id^='DashboardHistoricChartPanel'] > div > header > h2 span.hp-name"));


    /**
     * This object is used to interact with the <b>Lable of single system panel</b> of System in single system dashboard view.
     */
    public final TextLink systemLabelSingleSystem = new TextLink(By.cssSelector("[id^='DashboardSingleSystemPanel']> div > header > h2 > a > span"));

    /**
     **
     * This object is used to interact with the <b>System Edit</b> link shown on the Dashboard.
     */

    public final TextLink systemEditLink = new TextLink(By.cssSelector("div[id^='DashboardSingleSystemPanel'] > div > div.hp-page-splash > div > a > span"));

    /**
     **
     * This object is used to interact with the <b>System Label</b> label shown on the Dashboard.
     */
   public final TextLink systemTextLink = new TextLink(By.cssSelector("div[id^='DashboardSingleSystemPanel'] > div > header > h2 span.hp-name"));

    /**
     **
     * This object is used to interact with the <b>System Delete Icon</b> label shown on the Dashboard.
     */
    public final TextLink systemDeleteIcon = new TextLink(By.cssSelector("div[id^='DashboardSingleSystemPanel'] > div > header > div > div.hp-controls > div.hp-close"));


    /**
     * This object is used to interact with the <b>Lable of single system panel</b> of System in single system dashboard view.
     */
    public final TextLink singleStorageSystemText = new TextLink(By.cssSelector("div[id^='DashboardSingleSystemPanel'] > div > header > h2"));


    /**
     * This object is used to interact with the <b>Delete Textlink</b> of System in single system dashboard view.
     */
    public final TextLink deleteSystemOnSingleSystem = new TextLink(By.cssSelector("div[id^='DashboardSingleSystemPanel'] div[class='hp-close']"));

    /**
     **
     * This object is used to interact with the <b>System name</b> label shown on the Dashboard.
     */
    public final Text systemName = new Text(By.cssSelector("#ssmc-dashboard-single-system-panel legend > h2 > span[name='headerTitle'] + span"));

    /**
     * This object is used to interact with the <b>Delete icon of default capacity efficiency panel</b> shown in Dash Board
     */

    public final TextLink capacityEfficiencyEditIcon = new TextLink(By.cssSelector("div[id^='DashboardCapacityEfficiencyPanel']> div > header > div > div.hp-icon.hp-row-controls.hp-edit"));


    /**
     * This object is used to interact with the <b>Single system capacity efficiency panel</b> shown in Dash Board
     */

    public final TextLink singleCapacityEfficiencyText = new TextLink(By.cssSelector("div[id^='DashboardCapacityEfficiencyPanel']> div > header > h2 span.hp-name"));

    /**
     * This object is used to interact with the <b>Single system capacity efficiency panel edit icon</b> shown in Dash Board
     */

    public final TextLink singleCapacityEfficiencyEditIcon = new TextLink(By.cssSelector("div[id^='DashboardCapacityEfficiencyPanel'] > div > header > div > div.hp-icon.hp-row-controls.hp-edit"));


    /**
     * This object is used to interact with the <b>Single system capacity efficiency panel close icon</b> shown in Dash Board
     */

    public final TextLink singleCapacityEfficiencyDeleteIcon = new TextLink(By.cssSelector("div[id^='DashboardCapacityEfficiencyPanel'] > div > header > div > div.hp-controls > div.hp-close"));


    /**
     * This object is used to interact with the <b>FC label of device type capacity panel</b> shown in Dash Board
     */

    public final Text deviceTypeCapacityFCText = new Text(By.xpath("//div[starts-with(@id,'DashboardDeviceTypeCapacityPanel')]//label[text()='FC']"));

    /**
     * This object is used to interact with the <b>SSD label of device type capacity panel</b> shown in Dash Board
     */

    public final Text deviceTypeCapacitySSDText = new Text(By.xpath("//div[starts-with(@id,'DashboardDeviceTypeCapacityPanel')]//label[text()='SSD']"));


    /**
     * This object is used to interact with the <b>NL label of device type capacity panel</b> shown in Dash Board
     */

    public final Text deviceTypeCapacityNLText = new Text(By.xpath("//div[starts-with(@id,'DashboardDeviceTypeCapacityPanel')]//label[text()='NL']"));


    /**
     * This object is used to interact with the <b>Single system device type capacity panel</b> shown in Dash Board
     */

    public final TextLink singleDeviceTypeCapacityText = new TextLink(By.cssSelector("div[id^='DashboardDeviceTypeCapacityPanel'] > div > header > h2 span.hp-name"));

    /**
     * This object is used to interact with the <b>Delete icon of default device type capacity panel</b> shown in Dash Board
     */

    public final TextLink deviceTypeCapacityEditIcon = new TextLink(By.cssSelector("div[id^='DashboardDeviceTypeCapacityPanel']> div > header > div > div.hp-icon.hp-row-controls.hp-edit"));

    /**
     * This object is used to interact with the <b>Single system device type capacity edit icon</b> shown in Dash Board
     */

    public final TextLink singleDeviceTypeEditIcon = new TextLink(By.cssSelector("div[id^='DashboardDeviceTypeCapacityPanel'] > div > header > div > div.hp-icon.hp-row-controls.hp-edit"));


    /**
     * This object is used to interact with the <b>Single system device type capacity delete icon</b> shown in Dash Board
     */

    public final TextLink singleDeviceTypeDeleteIcon = new TextLink(By.cssSelector("div[id^='DashboardDeviceTypeCapacityPanel'] > div > header > div > div.hp-controls > div.hp-close"));


    /**
     * This object is used to interact with the <b>Edit Performance textlink</b> of System in single system dashboard view.
     */
    public final TextLink editPerformanceTextLink=new TextLink(By.cssSelector("[id^='DashboardPerformancePanel'] > div > header > div > div.hp-icon.hp-row-controls.hp-edit"));

    /**
     * This object is used to interact with the <b>Raw Capacity Grid Text Link</b> of System in All system dashboard view.
     */
    public final TextLink rawCapacityGridText= new TextLink(By.cssSelector("div[id^='DashboardRawCapacityPanel'] > div > header > h2 span.hp-name"));

    /**
     * This object is used to interact with the <b>Raw Capacity Panel SSD label</b> label shown on the Dashboard.
     */
    public final Text rawCapacitySSDlabel = new Text(By.cssSelector("#ssmc-dashboard-rawcapacityUsage-SSD-value"));

    /**
     * This object is used to interact with the <b>Raw Capacity Panel FC label</b> label shown on the Dashboard.
     */
    public final Text rawCapacityFClabel = new Text(By.cssSelector("#ssmc-dashboard-rawcapacityUsage-FC-value"));

    /**
     * This object is used to interact with the <b>Raw Capacity Panel NL label</b> label shown on the Dashboard.
     */
    public final Text rawCapacityNLabel = new Text(By.cssSelector("#ssmc-dashboard-rawcapacityUsage-NL-value"));






    /**
     * This object is used to interact with the <b>Raw Capacity Grid Delete Icon</b> of System in All system dashboard view.
     */
    public final TextLink rawCapacityDeleteIcon = new TextLink(By.cssSelector("div[id^='DashboardRawCapacityPanel'] > div > header > div > div.hp-controls > div.hp-close"));


    /**
     * This object is used to interact with the <b>Edit Raw Capacity  Grid </b> of System in single system dashboard view.
     */
    public final TextLink editRawCapacityText=new TextLink(By.cssSelector("div[id^='DashboardRawCapacityPanel'] > div > header > div > div.hp-icon.hp-row-controls.hp-edit"));

    /**
     * This object is used to interact with the <b>GEt Raw Capacity  Grid </b> of System in single system dashboard view.
     */
    public final Text getRawCapacityFCGridText=new Text(By.id("ssmc-dashboard-rawcapacityUsage-FC-wrapper"));

    /**
     * This object is used to interact with the <b>GEt Raw Capacity  Grid </b> of System in single system dashboard view.
     */
    public final Text getRawCapacityNLGridText=new Text(By.id("ssmc-dashboard-rawcapacityUsage-NL-wrapper"));


    /**
     * This object is used to interact with the <b>Device Type Capacity Edit Icon</b> shown on the Dashboard.
     */
    public final TextLink deviceTypeCapacityPanelEditIcon = new TextLink(By.cssSelector("[id^='DashboardDeviceTypeCapacityPanel']> div > header > div > div.hp-icon.hp-row-controls.hp-edit"));

    /**
     * This object is used to interact with the <b>system Panel Text Link /b> when two system panel shown in Dash Board.
     */

    public final Text selectedSytemPageText = new Text(By.cssSelector("#ssmc-dashboard-single-system-panel legend > h2 > span[name='headerTitle'] + span"));

    /**
     * This object is used to interact with the <b>System performance label</b> shown on the Dashboard page.
     */
    public final TextLink sytemPerformanceLabel = new TextLink(By.cssSelector("div[id^='DashboardSinglePerformancePanel'] > div > header > h2 > span.hp-name"));


    /**
     * This object is used to interact with the <b>System performance edit link</b> shown on the Dashboard page.
     */
    public final TextLink sytemPerformanceEditlink = new TextLink(By.cssSelector("div[id^='DashboardSinglePerformancePanel'] > div > header > div > div.hp-icon.hp-row-controls.hp-edit"));


    /**
     * This object is used to interact with the <b>System performance label </b> shown on system page the Dashboard page.
     */
    public final TextLink singleSytemPerformancePerformanceTextLabel = new TextLink(By.cssSelector("div[id^='DashboardSinglePerformancePanel'] > div > header > h2 > a > span"));


    /**
     * This object is used to interact with the <b>System performance Edit Icon </b> shown on system page the Dashboard page.
     */
    public final TextLink singleSytemPerformancePerformanceEditLink = new TextLink(By.cssSelector("div[id^='DashboardSinglePerformancePanel'] > div > header > div > div.hp-icon.hp-row-controls.hp-edit"));

    /**
     * This object is used to interact with the <b>Raw Capacity edit icon</b> shown on the System Dashboard page.
     */
    public final TextLink rawCapacityEditIcon = new TextLink(By.cssSelector("[id^='DashboardRawCapacityPanel']> div > header > div > div.hp-icon.hp-row-controls.hp-edit"));

    /**
     * This object is used to interact with the <b> Historical capacity Label edit link</b> shown on the Dashboard page.
     */
    public final TextLink historicalCapacityLabelEditlink = new TextLink(By.cssSelector("[id^=DashboardHistoricChartPanel] > div > header > div > div.hp-icon.hp-row-controls.hp-edit"));

    /**
     **
     * This object is used to interact with the <b>Edited System Label</b> label shown on the Dashboard.
     */
    public final TextLink changedSystemTextLink = new TextLink(By.xpath("(//div[starts-with(@id,\"DashboardSingleSystemPanel\")])[2]//span[@class='hp-name']"));


    /**
     **
     * This object is used to interact with the <b>Delete icon of edited single system panel</b> label shown on the Dashboard.
     */
    public final TextLink changedSystemDeleteLink = new TextLink(By.xpath("(//div[starts-with(@id,'DashboardSingleSystemPanel')])[2]//div[@class='hp-close'][1]"));


    /**
     **
     * This object is used to interact with the <b>System Edit pencil icon</b> link shown on the Dashboard.
     */

    public final TextLink systemEditPencilIcon = new TextLink(By.cssSelector("div[id^='DashboardSingleSystemPanel'] > div > header > div > div.hp-icon.hp-row-controls.hp-edit"));

    /**
     **
     * This object is used to interact with the <b>Total allocated capacity size</b>  shown on the Dashboard.
     */
    public final Text totalAllocatedCapacitySize = new Text(By.cssSelector("label[id$='-donut-tot']"));

    /**
     **
     * This object is used to interact with the <b>Total capacity text</b>  shown on the Dashboard.
     */
    public final Text totalCapacity = new Text(By.cssSelector("label[id$='-donut-total']"));

    /**
     **
     * This object is used to interact with the <b>Total capacity Panel</b>  shown on the Dashboard.
     */
    public final Text totalCapacityPanel1 = new Text(By.cssSelector("#DashboardTotalCapacityPanel >div >div"));

    /**
     **
     * This object is used to interact with the <b>Allocated capacity Panel</b>  shown on the Dashboard.
     */
    public final Text allocatedCapacityPanel1 = new Text(By.cssSelector("#DashboardAllocCapacityPanel>div >div"));

    /**
     **
     * This object is used to interact with the <b>Single system total capacity label</b>  shown on the Dashboard.
     */
    public final Text totalCapacityPanel_EachSystem = new Text(By.cssSelector("div[id^='DashboardTotalCapacityPanel'] > div > header > h2 span.hp-name"));

    /**
     **
     * This object is used to interact with the <b>Single system allocated capacity label</b>  shown on the Dashboard.
     */
    public final Text allocatedCapacityPanel_EachSystem = new Text(By.cssSelector("div[id^='DashboardAllocCapacityPanel'] > div > header > h2 span.hp-name"));

    /**
     **
     * This object is used to interact with the <b>Single system allocated capacity delete icon</b>  shown on the Dashboard.
     */
    public final TextLink totalCapacityPanelDeleteIcon = new TextLink(By.cssSelector("div[id^='DashboardTotalCapacityPanel'] > div > header > div > div.hp-controls > div.hp-close"));


    /**
     **
     * This object is used to interact with the <b>Single system total capacity label</b>  shown on the Dashboard.
     */
    public final TextLink allocatedCapacityPanelDeleteIcon = new TextLink(By.cssSelector("div[id^='DashboardAllocCapacityPanel'] > div > header > div > div.hp-controls > div.hp-close"));

    /**
     **
     * This object is used to interact with the <b>Total capacity edit icon</b>  shown on the Dashboard.
     */
    public final TextLink totalCapacityPanelEdiIcon_EachSystem = new TextLink(By.cssSelector("div[id^='DashboardTotalCapacityPanel'] > div > header > div > div.hp-icon.hp-row-controls.hp-edit"));

    /**
     **
     * This object is used to interact with the <b>Allocated capacity edit icon</b>  shown on the Dashboard.
     */
    public final Text allcoatedCapacityPanelEdiIcon_EachSystem = new Text(By.cssSelector("div[id^='DashboardAllocCapacityPanel']  > div > header > div > div.hp-icon.hp-row-controls.hp-edit"));

    /**
     * This object is used to interact with the <b>Common Actions and Views</b> label shown on the Dashboard.
     */

    public final TextLink commonActionsAndViewsLabel = new TextLink(By.cssSelector("div[id^='DashboardActionsPanel'] > div > header > h2 span.hp-name"));

    /**
     * This object is used to interact with the <b>Common Actions and Views Edit Icon</b> shown in Dash Board
     */

    public final TextLink commonActionsAndViewsEditIcon = new TextLink(By.cssSelector("div[id^='DashboardActionsPanel'] > div > header > div > div.hp-icon.hp-row-controls.hp-edit"));

    /**
     * This object is used to interact with the <b>Common Actions and Views Delete Icon</b> shown on the Dashboard.
     */

    public final TextLink commonActionsAndViewsDeleteIcon = new TextLink(By.cssSelector("div[id^='DashboardActionsPanel'] > div > header > div > div.hp-controls > div.hp-close"));

    /**
     * This object is used to interact with the <b>Daily Top volume bandwidth</b> label shown on the Dashboard.
     */

    public final TextLink dailyTopVolumeBandwidthLabel = new TextLink(By.cssSelector("div[id^='DashboardVolumeBandwidthPanel'] > div > header > h2 span.hp-name"));

    /**
     * This object is used to interact with the <b>Daily Top volume bandwidth Edit Icon</b> shown in Dash Board
     */

    public final TextLink dailyTopVolumeBandwidthEditIcon = new TextLink(By.cssSelector("div[id^='DashboardVolumeBandwidthPanel'] > div > header > div > div.hp-icon.hp-row-controls.hp-edit"));

    /**
     * This object is used to interact with the <b>Daily Top volume bandwidth Delete Icon</b> shown on the Dashboard.
     */

    public final TextLink dailyTopVolumeBandwidthDeleteIcon = new TextLink(By.cssSelector("div[id^='DashboardVolumeBandwidthPanel'] > div > header > div > div.hp-controls > div.hp-close"));

    /**
     * This object is used to interact with the <b>File Persona status</b> label shown on the Dashboard.
     */

    public final TextLink filePersonaStatusLabel = new TextLink(By.cssSelector("div[id^='DashboardFilePersonaStatusPanel'] > div > header > h2 span.hp-name"));

    /**
     * This object is used to interact with the <b>File Persona status Edit Icon</b> shown in Dash Board
     */

    public final TextLink filePersonaStatusEditIcon = new TextLink(By.cssSelector("div[id^='DashboardFilePersonaStatusPanel'] > div > header > div > div.hp-icon.hp-row-controls.hp-edit"));

    /**
     * This object is used to interact with the <b>File Persona status Delete Icon</b> shown on the Dashboard.
     */

    public final TextLink filePersonaStatusDeleteIcon = new TextLink(By.cssSelector("div[id^='DashboardFilePersonaStatusPanel'] > div > header > div > div.hp-controls > div.hp-close"));

    /**
     * This object is used to interact with the <b>Block Persona status</b> label shown on the Dashboard.
     */

    public final TextLink blockPersonaStatusLabel = new TextLink(By.cssSelector("div[id^='DashboardBlockPersonaStatusPanel'] > div > header > h2 span.hp-name"));

    /**
     * This object is used to interact with the <b>Block Persona status Edit Icon</b> shown on the Dash Board
     */

    public final TextLink blockPersonaStatusEditIcon = new TextLink(By.cssSelector("div[id^='DashboardBlockPersonaStatusPanel'] > div > header > div > div.hp-icon.hp-row-controls.hp-edit"));

    /**
     * This object is used to interact with the <b>Block Persona status Delete Icon</b> shown on the Dashboard.
     */

    public final TextLink blockPersonaStatusDeleteIcon = new TextLink(By.cssSelector("div[id^='DashboardBlockPersonaStatusPanel'] > div > header > div > div.hp-controls > div.hp-close"));

    /**
     * This object is used to interact with the <b> No Active volumes</b> label of daily top host bandwidth shown on the Dashboard.
     */

    public final Text noActiveVolumeLabel = new Text(By.cssSelector("div[id^='DashboardVolumeBandwidthPanel'] > div > div.hp-page-splash > div"));

    /**
     * This object is used to interact with the <b>Block Persona status Delete Icon</b> shown on the Dashboard.
     */

    public final TextLink testValue = new TextLink(By.cssSelector("#ssmc-dashboard-topVolumeBandwidth > li a:contains('cgvvtesting')"));
    /**
     **
     * This object is used to interact with the <b>LHS Filter Panel Option</b>  shown on the Dashboard on 3.1.0.
     */
    public final TextLink lhsFilterPanelOption = new TextLink(By.cssSelector(".hp-pin-left"));


    /**
     **
     * This object is used to interact with the <b>All filter options</b>  on status filter dropdown.
     */
    public final CheckBox filterStatusAllCheckbox = new CheckBox(By.cssSelector("input[data-id='all']"));

    /**
     **
     * This object is used to interact with the <b>Critical filter options</b>  on status filter dropdown.
     */
    public final CheckBox filterStatusCriticalCheckbox = new CheckBox(By.cssSelector("input[data-id='Critical']"));

    /**
     **
     * This object is used to interact with the <b>Warning filter options</b>  on status filter dropdown.
     */
    public final CheckBox filterStatusWarningCheckbox = new CheckBox(By.cssSelector("input[data-id='Warning']"));

    /**
     **
     * This object is used to interact with the <b>OK filter options</b>  on status filter dropdown.
     */
    public final CheckBox filterStatusOkCheckbox = new CheckBox(By.cssSelector("input[data-id='OK']"));

    /**
     **
     * This object is used to interact with the <b>Unknown filter options</b>  on status filter dropdown.
     */
    public final TextLink filterStatusUnknownCheckbox = new TextLink(By.cssSelector("input[data-id='Unknown']"));

    /**
     **
     * This object is used to interact with the <b>Comparator selector</b>  on status filter dropdown.
     */
    public final TextLink filterComparatorLink = new TextLink(By.cssSelector("div[data-filter-property='numberExports'] > div[class='ssmc-filter-comparator ssmc-closed']"));

    /**
     **
     * This object is used to interact with the <b>export filter text box</b>  on status filter dropdown.
     */
    public final TextField filtertextBox = new TextField(By.cssSelector(".hp-numeric"));

    /**
     **
     * This object is used to interact with the <b>first filter dropdown</b>  on status filter dropdown.
     */
    public final TextLink filtertextLink = new TextLink(By.cssSelector("div[data-filter-property='numberExports'] > div[class='ssmc-filter-name ssmc-closed']"));

    /**
     **
     * This object is used to interact with the <b>Status filter radio button</b>  on status filter dropdown.
     */
    public final RadioButton filterStatusRadioButton = new RadioButton(By.cssSelector("input[value='status']"),By.cssSelector("input[value='status']~label"));


//    public final FilterSelectorMenu filterComparatorSelector = new FilterSelectorMenu(By.cssSelector("div[data-filter-property='numberExports'] > div[class='ssmc-options ssmc-compare-options']"));

    /**
     **
     * This object is used to interact with the <b> Less than Comparator selector</b>  on status filter dropdown.
     */
    public final TextLink filterComparatorLessThanLink = new TextLink(By.cssSelector("label[data-id='<']"));



    /**
     **
     * This object is used to interact with the <b> Greater than Comparator selector</b>  on status filter dropdown.
     */
    public final TextLink filterComparatorGreaterThanLink = new TextLink(By.cssSelector("label[data-id='>']"));

    /**
     **
     * This object is used to interact with the <b> Greater equals Comparator selector</b>  on status filter dropdown.
     */
    public final TextLink filterComparatorGreaterEqualsLink = new TextLink(By.cssSelector("label[data-id='>=']"));

    /**
     **
     * This object is used to interact with the <b> Lesser equals Comparator selector</b>  on status filter dropdown.
     */
    public final TextLink filterComparatorLessEqualsLink = new TextLink(By.cssSelector("label[data-id='<=']"));

    /**
     **
     * This object is used to interact with the <b>Equals Comparator selector</b>  on status filter dropdown.
     */
    public final TextLink filterComparatorEqualsLink = new TextLink(By.cssSelector("label[data-id='=']"));

    /**
     **
     * This object is used to interact with the <b>Reset link</b>  on status filter dropdown.
     */
    public final TextLink filterResetLink = new TextLink(By.cssSelector("div.hp-filters >a"));

    /**
     * This object is used to interact with the <b>Storage Systems</b> label shown on the single Dashboard.
     */
    public final TextLink singleStorageSystemsLabel = new TextLink(By.cssSelector("div[id^='DashboardSystemsPanel'] > div > header > h2 > a > span.hp-name"));

    /**
     * This object is used to interact with the <b>Storage Systems edit icon</b> link shown on the single Dashboard.
     */
    public final TextLink singleStorageSystemEditIcon = new TextLink(By.cssSelector("div[id^='DashboardSystemsPanel'] >div > header > div > div.hp-icon.hp-row-controls.hp-edit"));

    /**
     * This object is used to interact with the <b>Storage Systems close icon</b> link shown on the single Dashboard.
     */
    public final TextLink singleStorageSystemDeleteIcon = new TextLink(By.cssSelector("div[id^='DashboardSystemsPanel'] >div > header > div > div >div"));

    /**
     * This object is used to interact with the <b>Exports column checkbox</b>  on status filter dropdown.
     */
    public final CheckBox exportsColumnCheckbox = new CheckBox(By.cssSelector("input[data-stid='stHost']"));

    /**
     * This object is used to interact with the <b>Storage Systems Header</b> label shown on the Dashboard.
     */
    public final Text storageSystemsLabelHeader = new Text(By.cssSelector("div[id^='DashboardSystemsPanel']> div > header > h2"));

    /**
     * This object is used to interact with the <b>Performance Header</b> label shown on the Dashboard.
     */
    public final Text performanceLabelHeader = new Text(By.cssSelector("div[id^='DashboardPerformancePanel']> div > header > h2"));

    /**
     * This object is used to interact with the <b>Total Capacity Header</b> label shown on the Dashboard.
     */
    public final Text totalCapacityLabelHeader = new Text(By.cssSelector("div[id^='DashboardTotalCapacityPanel']> div > header > h2"));

    /**
     * This object is used to interact with the <b>Allocated Capacity Header</b> label shown on the Dashboard.
     */
    public final Text allocatedCapacityLabelHeader = new Text(By.cssSelector("div[id^='DashboardAllocCapacityPanel']> div > header > h2"));

    /**
     * This object is used to interact with the <b>Device Type Capacity Header</b> label shown on the Dashboard.
     */
    public final Text deviceTypeCapacityLabelHeader = new Text(By.cssSelector("div[id^='DashboardDeviceTypeCapacityPanel']> div > header > h2"));

    /**
     * This object is used to interact with the <b>Capacity Efficiency Header</b> label shown on the Dashboard.
     */
    public final Text capacityEfficiencyLabelHeader = new Text(By.cssSelector("div[id^='DashboardCapacityEfficiencyPanel']> div > header > h2"));

    /**
     * This object is used to interact with the <b>Historical Capacity Header</b> label shown on the Dashboard.
     */
    public final Text historicalCapacityLabelHeader = new Text(By.cssSelector("div[id^='DashboardHistoricChartPane'] > div > header > h2"));

    /**
     * This object is used to interact with the <b>Raw Capacity Header</b> label shown on the Dashboard.
     */
    public final Text rawCapacityLabelHeader = new Text(By.cssSelector("div[id^='DashboardRawCapacityPanel'] > div > header > h2"));

    /**
     * This object is used to interact with the <b>activity Capacity Header</b> label shown on the Dashboard.
     */
    public final Text activityLabelHeader = new Text(By.cssSelector("div[id^='DashboardActivityPanel'] > div > header > h2"));

    /**
     * This object is used to interact with the <b>common actions and views Header</b> label shown on the Dashboard.
     */
    public final Text commonActionsAndViewsLabelHeader = new Text(By.cssSelector("div[id^='DashboardActionsPanel'] > div > header > h2"));

    /**
     * This object is used to interact with the <b>Dashboard Bandwidth Panel Header</b> label shown on the Dashboard.
     */
    public final Text dailyTopHostBandwithLabelHeader = new Text(By.cssSelector("div[id^='DashboardBandwidthPanel'] > div > header > h2"));

    /**
     * This object is used to interact with the <b>Dashboard Port Panel Header</b> label shown on the Dashboard.
     */
    public final Text dailyTopHostPortUtilizationLabelHeader = new Text(By.cssSelector("div[id^='DashboardPortPanel'] > div > header > h2"));
    /**
     * This object is used to interact with the <b>Dashboard Single SystemPanel Header</b> label shown on the Dashboard.
     */
    public final TextLink systemLabelHeader = new TextLink(By.cssSelector("div[id^='DashboardSingleSystemPanel'] > div > header > h2"));
    /**
     * This object is used to interact with the <b>Dashboard Single Performance Panel Header</b> label shown on the Dashboard.
     */
    public final Text systemPerformanceLabelHeader = new Text(By.cssSelector("div[id^='DashboardSinglePerformancePanel'] > div > header > h2"));
    /**
     * This object is used to interact with the <b>Dashboard File Persona Status Panel Header</b> label shown on the Dashboard.
     */
    public final Text filePersonalstatusLabelHeader = new Text(By.cssSelector("div[id^='DashboardFilePersonaStatusPanel'] > div > header > h2"));
    /**
     * This object is used to interact with the <b>Dashboard BlockPersona  StatusPanelHeader</b> label shown on the Dashboard.
     */
    public final Text blockpersonaLabelHeader = new Text(By.cssSelector("div[id^='DashboardBlockPersonaStatusPanel'] > div > header > h2"));
    /**
     * This object is used to interact with the <b>common actions and views Header</b> label shown on the Dashboard.
     */
    public final Text volumebandwithLabelHeader = new Text(By.cssSelector("div[id^='DashboardVolumeBandwidthPanel'] > div > header > h2"));
    /**


    /**
     * This object is used to interact with the <b>Raw Capacity panel Header</b> of add panels dialog.
     */

    public final Text singleSystemRawCapacityPanelHeader = new Text(By.cssSelector("#DashboardRawCapacityPanel > div > header > h2"));

    /**
     * This object is used to interact with the <b>Activity panel Header</b> of add panels dialog.
     */

    public final Text singleSystemActivityPanelHeader = new Text(By.cssSelector("#DashboardActivityPanel > div > header > h2"));

    /**
     * This object is used to interact with the <b>Common Actions and Views panel Header</b> of add panels dialog.
     */

    public final Text singleSystemCommonActionsAndViewsPanelHeader = new Text(By.cssSelector("#DashboardActionsPanel > div > header > h2"));

    /**
     * This object is used to interact with the <b>Single system Daily Top Host Bandwidth panel Header</b> of add panels dialog.
     */

    public final Text singleSystemDailyTopHostBandwidthPanelHeader = new Text(By.cssSelector("#DashboardBandwidthPanel > div > header > h2"));

    /**
     * This object is used to interact with the <b>Storage Systems Header</b> label shown on the Dashboard.
     */
    public final Text singleSystemStorageSystemsHeader = new Text(By.cssSelector("#DashboardSingleSystemPanel > div > header > h2"));


    /**
     * This object is used to interact with the <b>Performance Header</b> label shown on the Dashboard.
     */
    public final Text singleSystemPerformanceLabelHeader = new Text(By.cssSelector("div[id^='DashboardSinglePerformancePanel'] > div > header > h2"));

    /**
     * This object is used to interact with the <b>System Summary</b> label shown on the Dashboard.
     */

    public final TextLink systemSummaryLabel = new TextLink(By.cssSelector("div[id^='DashboardSingleSystemSummaryPanel'] > div > header > h2 span.hp-name"));

    /**
     * This object is used to interact with the <b>System Summary Edit Icon</b> shown on the Dashboard.
     */
    public final TextLink systemSummaryEditIcon = new TextLink(By.cssSelector("div[id^='DashboardSingleSystemSummaryPanel'] > div > header > div > div.hp-icon.hp-row-controls.hp-edit"));

    /**
     * This object is used to interact with the <b>System Summary Delete Icon</b> shown on the Dashboard.
     */
    public final TextLink systemSummaryDeleteIcon = new TextLink(By.cssSelector("div[id^='DashboardSingleSystemSummaryPanel'] > div > header > div > div.hp-controls > div.hp-close"));

    /**
     * This object is used to interact with the <b>System Summary Edit Icon</b> shown on the Dashboard.
     */
    public final TextLink systemSummaryEditLink = new TextLink(By.cssSelector("div[id^='DashboardSingleSystemSummaryPanel'] > div > div.hp-page-splash > div > a"));

    /**
     * This object is used to interact with the <b>Performance Time Text</b> shown on the Dashboard.
     */
    public final Text performanceTimeText = new Text(By.cssSelector("td[name='timeRange']"));
	
	/**
     * This object is used to interact with the <b>Multiple Port</b> shown on the Dashboard.
     */
	 public final TextLink multiplePortTextLink = new TextLink(By.cssSelector("div[id='ssmc-dashboard-topHostPortBandwidth']"));

    /**
     * This object is used to interact with the <b> Close Button </b> of Dashboard Panel.
     */
    public final Button closeButton = new Button(By.cssSelector(".hp-ok.hp-cancel.hp-primary"),"Close");

    /**
     * This object is used to interact with the <b> Create Dashboard Text link </b> of Dashboard Panel.
     */
    public final TextLink createDashboardTextLink = new TextLink(By.cssSelector("a[data-localize='dashboard.createDashboardLink']"));

    /**
     * This object is used to interact with the <b> Capacity Efficiency Info</b> of this dialog.
     */
    public final TextLink capacityEfficiencyIcon = new TextLink(By.id("ssmc-dashboard-capacity-efficiency-panel-ssmcInfoIcon"));

    /**
     * This object is used to interact with the <b> Capacity Efficiency Information</b> of this dialog.
     */
    public final Text capacityEfficiencyInformation = new Text(By.id("ssmc-dashboard-capacity-efficiency-panel-ssmcInfoIcon-tooltip"));

    /**
     * This object is used to interact with the <b> Capacity Efficiency label</b> on CPG overview
     */
    public final Text capacityEfficiencyLabelOnCpg = new Text(By.cssSelector("span[data-localize='cpgs.show.showOverview.capacityEfficiency.label']"));

    /**
     * This object is used to interact with the <b> Capacity Efficiency Info</b> on CPG overview
     */
    public final TextLink capacityEfficiencyIconOnCpg = new TextLink(By.id("ssmc-cpgs-general-efficiency-ssmcInfoIcon"));

    /**
     * This object is used to interact with the <b> Capacity Efficiency Information</b> on CPG overview
     */
    public final Text capacityEfficiencyInformationOnCpg = new Text(By.id("ssmc-cpgs-general-efficiency-ssmcInfoIcon-tooltip"));

    /**
     * This object is used to interact with the <b> Capacity Efficiency Info</b> of this dialog.
     */
    public final TextLink totalCapacityIcon = new TextLink(By.id("ssmc-dashboard-total-capacity-panel-ssmcInfoIcon"));

    /**
     * This object is used to interact with the <b> Total Capacity Information</b> of this dialog.
     */
    public final Text totalCapacityInformation = new Text(By.id("ssmc-dashboard-total-capacity-panel-ssmcInfoIcon-tooltip"));

    /**
     * This object is used to interact with the <b>Capacity Efficiency FieldSet</b> in the <b>Systems </b> pane.
     */
    public final Text systemsCapacityEfficiency = new Text(By.cssSelector("span[data-localize='hardware.systems.show.compaction.efficiency']"));

    /**
     * This object is used to interact with the <b>Information icon under Capacity Efficiency panel</b>, listed on the Systems panel.
     */
    public final TextLink systemsInfoIconButton = new TextLink(By.cssSelector("#ssmc-hw-systems-compaction-capacityEfficiency-ssmcInfoIcon"));

    /**
     * This object is used to interact with the <b>Information icon tooltip under Capacity Efficiency panel</b>, listed on the Systems panel.
     */
    public final Text systemsInfoIcontooltip = new Text(By.cssSelector("#ssmc-hw-systems-compaction-capacityEfficiency-ssmcInfoIcon-tooltip"));

    /**
     * This object is used to interact with the <b>Capacity Effciency label</b>, listed on the Virtual Volumes panel.
     */
    public final Text vvCapacityEfficiencyLabel = new Text(By.cssSelector("span[data-localize='provisioning.virtual-volumes.capacity.capacityEfficiencyTitle']"));


    /**
     * This object is used to interact with the <b>Information icon under Capacity Efficiency panel</b>, listed on the Virtual Volumes panel.
     */
    public final TextLink vvInfoIconButton = new TextLink(By.cssSelector("#ssmc-virtual-volumes-capacity-capacityEfficiency-ssmcInfoIcon"));

    /**
     * This object is used to interact with the <b>Information icon tooltip under Capacity Efficiency panel</b>, listed on the Virtual Volumes panel.
     */
    public final Text vvInfoIcontooltip = new Text(By.cssSelector("#ssmc-virtual-volumes-capacity-capacityEfficiency-ssmcInfoIcon-tooltip"));

    /**
     * This object is used to interact with the <b>Capacity Effciency label</b>, listed on the Virtual machines panel.
     */

    public final Text vmCapacityEfficiencyLabel = new Text(By.cssSelector("span[data-localize='provisioning.vv-sets.capacity.capacityEfficiencyTitle']"));

    /**
     * This object is used to interact with the <b>Information icon under Capacity Efficiency panel</b>, listed on the Virtual Machines panel.
     */
    public final TextLink vmInfoIconButton = new TextLink(By.cssSelector("#ssmc-virtual-machines-capacity-efficiency-ssmcInfoIcon"));

    /**
     * This object is used to interact with the <b>Information icon tooltip under Capacity Efficiency panel</b>, listed on the Virtual Machines panel.
     */
    public final Text vmInfoIcontooltip = new Text(By.cssSelector("#ssmc-virtual-machines-capacity-efficiency-ssmcInfoIcon-tooltip"));

    /**
     * This object is used to interact with the <b>Capacity Effciency label</b>, listed on the Storage-Containers panel.
     */
    public final Text storageContainerCapacityEfficiencyLabel = new Text(By.cssSelector("span[data-localize='provisioning.vmware.titles.capacityEfficiency']"));

    /**
     * This object is used to interact with the <b>Information icon under Capacity Efficiency panel</b>, listed on the Storage-Containers panel.
     */
    public final TextLink storageContainerInfoIconButton = new TextLink(By.cssSelector("#ssmc-storage-containers-capacity-efficiency-ssmcInfoIcon"));

    /**
     * This object is used to interact with the <b>Information icon tooltip under Capacity Efficiency panel</b>, listed on the Storage-Containers panel.
     */
    public final Text storageContainerInfoIcontooltip = new Text(By.cssSelector("#ssmc-storage-containers-capacity-efficiency-ssmcInfoIcon-tooltip"));


    /**
     * This object is used to interact with the <b>Capacity Efficiency</b>, listed on the Domains panel.
     */
    public final Text domainCapacityEfficiencyLabel = new Text(By.cssSelector("span[data-localize='domains.overview.capacityEfficiencyLabel']"));

    /**
     * This object is used to interact with the <b>Information icon under Capacity Efficiency panel</b>, listed on the Domains panel.
     */
    public final TextLink domainInfoIconButton = new TextLink(By.cssSelector("#ssmc-domains-general-efficiency-ssmcInfoIcon"));

    /**
     * This object is used to interact with the <b>Information icon tooltip under Capacity Efficiency panel</b>, listed on the Domains panel.
     */
    public final Text domainInfoIcontooltip = new Text(By.cssSelector("#ssmc-domains-general-efficiency-ssmcInfoIcon-tooltip"));

    /**
     * This object is used to interact with the <b> Capacity Efficiency icon</b> on sytem page..
     */
    public final TextLink totalCapacityIcon_systemPage = new TextLink(By.id("ssmc-hw-systems-capacity-diskCapacity-ssmcInfoIcon"));

    /**
     * This object is used to interact with the <b> Total Capacity Information</b> on system page.
     */
    public final Text totalCapacityInformation_systemPage = new Text(By.id("ssmc-hw-systems-capacity-diskCapacity-ssmcInfoIcon-tooltip"));

    /**
     **
     * This object is used to interact with the <b>Single system total capacity label</b>  shown on the Dashboard.
     */
    public final Text totalCapacityPanel_SystemPage = new Text(By.cssSelector("span[id='ssmc-hw-systems-capacity-diskCapacity-header']"));

    /**
     * This object is used to interact with the <b>capacityEfficiencySavingsValue</b> shown on the Dashboard.
     *
     */

    public final Text capacityEfficiencySavingsValue = new Text(By.id("DashboardCapacityEfficiencyPanel-donut-sav"));

    /**
     * This object is used to interact with the <b>capacityEfficiencyReservedValue</b> shown on the Dashboard.
     *
     */

    public final Text capacityEfficiencyUsedValue = new Text(By.id("DashboardCapacityEfficiencyPanel-donut-rsrvd"));

    /**
     * This object is used to interact with the <b>Protection Jobs Panel Text</b> of System in All system dashboard view.
     */
    public final TextLink protectionJobPanelText = new TextLink(By.cssSelector("div[id^='DashboardProtectionJobPanel'] > div > header > h2 span.hp-name"));

    /**
     * This object is used to interact with the <b>rmc Protected Volumes Panel Panel Text</b> of System in All system dashboard view.
     */
    public final TextLink rmcProtectedVolumesPanelMessageText = new TextLink(By.cssSelector("div[id^='DashboardRMCProtectedVolumesPanel'] > div > header > h2 span.hp-name"));
    /**
     * This object is used to interact with the <b>Protection Jobs Panel Text</b> of System in All system dashboard view.
     */
    public final Text protectionJobPanelDataMessage = new Text(By.cssSelector("div[id^='DashboardProtectionJobPanel']  div[class='hp-page-empty']"));

    /**
     * This object is used to interact with the <b>virtual Volume Protection Panel Edit Text</b> of virtual Volume Protection Panel
     */
    public final TextLink virtualVolumeProtectionPanelEditText = new TextLink(By.cssSelector("div[id^='DashboardVVProtectionPanel'] > div > header > div > div.hp-icon.hp-row-controls.hp-edit"));
    /**
     * This object is used to interact with the <b> Protection Job Edit Text</b> of virtual Volume Protection Panel
     */
    public final Text protectionJobEditText = new Text(By.cssSelector("div[id^='DashboardProtectionJobPanel'] > div > header > div > div.hp-icon.hp-row-controls.hp-edit"));

    /**
     * This object is used to interact with the <b>virtual volume protection  Delete Icon</b> of System in All system dashboard view.
     */
    public final TextLink virtualVolumeProtectionDeleteIcon = new TextLink(By.cssSelector("div[id^='DashboardVVProtectionPanel'] > div > header > div > div.hp-controls > div.hp-close"));

    /**
     * This object is used to interact with the <b>protection job Delete Icon</b> of System in All system dashboard view.
     */
    public final TextLink protectionJobDeleteIcon = new TextLink(By.cssSelector("div[id^='DashboardProtectionJobPanel'] > div > header > div > div.hp-controls > div.hp-close"));

    /**
     * This object is used to interact with the <b>Virtual Volume Protection Panel Header</b> label shown on the Dashboard.
     */
    public final TextLink virtualVolumeProtectionLabelHeader = new TextLink(By.cssSelector("div[id^='DashboardVVProtectionPanel'] > div > header > h2"));

    /**
     * This object is used to interact with the <b>Unprotected volumes</b> number of virtual volume protection shown on the Dashboard.
     */
    public final Text unprotectedVolumesNumber = new Text(By.id("ssmc-dashboard-vv-unprotected-count"));

    /**
     * This object is used to interact with the <b>Protected volumes</b> number of virtual volume protection shown on the Dashboard.
     */
    public final Text protectedVolumesNumber = new Text(By.id("ssmc-dashboard-vv-protected-count"));

    /**
     * This object is used to interact with the <b>Local snapshots </b> number of virtual volume protection shown on the Dashboard.
     */
    public final Text localSnapshotsNumber = new Text(By.id("ssmc-dashboard-vv-local-snapshot-count"));

    /**
     * This object is used to interact with the <b>Remote copy</b> number of virtual volume protection shown on the Dashboard.
     */
    public final Text remoteCopyNumber = new Text(By.id("ssmc-dashboard-vv-remote-copy-count"));

    /**
     * This object is used to interact with the <b>RMC protection</b> number of virtual volume protection shown on the Dashboard.
     */
    public final Text rmcProtectionNumber = new Text(By.id("ssmc-dashboard-vv-rmc-protection-count"));

    /**
     * This object is used to interact with the <b>Virtual Volume Protection Textlink</b> label shown on the Dashboard.
     */
    public final TextLink virtualVolumeProtectionLabelLink = new TextLink(By.cssSelector("div[id^='DashboardVVProtectionPanel'] > div >header>h2 span.hp-name"));

    /**
     * This object is used to interact with the <b>Virtual Volume Protection Panel </b> table shown on the Dashboard.
     */
    public final Table vvProtectionTable = new Table(By.id("ssmc-dashboard-vv-protection-table"));

    /**
     * This object is used to interact with the <b> RMC Protectected Volume Delete Icon</b> shown on dashboard view.
     */
    public final TextLink rmcProtectectedVolumeDeleteIcon = new TextLink(By.cssSelector("div[id^='DashboardRMCProtectedVolumesPanel'] > div > header > div > div.hp-controls > div.hp-close"));

    /**
     * This object is used to interact with the <b>rmc Protected Volumes Unprotected CountText</b> of System in All system dashboard view.
     */
    public final TextLink rmcProtectionJobsCompletedCountText = new TextLink(By.cssSelector("[id$='donut-normal']"));

    /**
     * This object is used to interact with the <b>rmc Protected Volumes Unprotected CountText</b> of System in All system dashboard view.
     */
    public final TextLink rmcProtectedVolumesUnprotectedCountText = new TextLink(By.cssSelector("[id$='donut-unprotected-dynamicLabel']"));

    /**
     * This object is used to interact with the <b> Virtual Volume Protection Protected Count Text </b> of System in All system dashboard view.
     */
    public final TextLink virtualVolumeProtectionprotectedCountText = new TextLink(By.cssSelector("[id$='ssmc-dashboard-vv-protected-count']"));

    /**
     * This object is used to interact with the <b>Virtual Volume Protection job Header Text</b> of System in All system dashboard view.
     */
    public final TextLink  clickHereRMCProtectionJobTextLink = new TextLink(By.cssSelector("div[id^='DashboardProtectionJobPanel'] div>a"));

    /**
     * This object is used to interact with the <b>Block value of allocated capacity pael</b> of dashboard panel
     */
    public final Text blockAllocatedCapacity = new Text(By.cssSelector("label[id^='DashboardAllocCapacityPanel'][id$='donut-block']"));

    /**
     * This object is used to interact with the <b>System value of allocated capacity panel</b> of dashboard panel
     */
    public final Text systemAllocatedCapacity = new Text(By.cssSelector("label[id^='DashboardAllocCapacityPanel'][id$='donut-sys']"));


    /**
     * This object is used to interact with the <b>Total value of allocated capacity panel</b> of dashboard panel
     */
    public final Text totalAllocatedCapacity = new Text(By.cssSelector("label[id^='DashboardAllocCapacityPanel'][id$='donut-tot']"));

    /**
     **
     * This object is used to interact with the <bAllocated capacity label</b>  shown on the Dashboard.
     */
    public final TextLink allocatedCapacityPanelEditIcon = new TextLink(By.cssSelector("div[id^='DashboardAllocCapacityPanel'] div.hp-icon.hp-row-controls.hp-edit"));

    /**
     * This object is used to interact with the <b>SSD allocated Raw capacity panel</b> of dashboard panel
     */
    public final Text ssdAllocatedRawCapacity = new Text(By.cssSelector("#ssmc-dashboard-rawcapacityUsage-SSD-legend  li.hp-utilization-value > span.hp-value"));

    /**
     * This object is used to interact with the <b>SSD free Raw capacity panel</b> of dashboard panel
     */
    public final Text ssdFreeRawCapacity = new Text(By.cssSelector("#ssmc-dashboard-rawcapacityUsage-SSD-legend  li.hp-utilization-total.hp-utilization-free > span.hp-value"));

    /**
     * This object is used to interact with the <b>SSD total Raw capacity panel</b> of dashboard panel
     */
    public final Text ssdTotalRawCapacity = new Text(By.id("ssmc-dashboard-rawcapacityUsage-SSD-value"));


    /**
     * This object is used to interact with the <b>FC allocated Raw capacity panel</b> of dashboard panel
     */
    public final Text fcAllocatedRawCapacity = new Text(By.cssSelector("#ssmc-dashboard-rawcapacityUsage-FC-legend  li.hp-utilization-value > span.hp-value"));

    /**
     * This object is used to interact with the <b>FC free Raw capacity panel</b> of dashboard panel
     */
    public final Text fcFreeRawCapacity = new Text(By.cssSelector("#ssmc-dashboard-rawcapacityUsage-FC-legend  li.hp-utilization-total.hp-utilization-free > span.hp-value"));

    /**
     * This object is used to interact with the <b>FC total Raw capacity panel</b> of dashboard panel
     */
    public final Text fcTotalRawCapacity = new Text(By.id("ssmc-dashboard-rawcapacityUsage-FC-value"));
    /*
     * This object is used to interact with the <b>Host Text Block Persona Status panel</b> of Dashboard.
     */
    public final Text hostStatusBlockPersona = new Text(By.id("ssmc-dashboard-status-label-hostStatus"));

    /**
     * This object is used to interact with the <b>VV Text Block Persona Status panel</b> of Dashboard.
     */
    public final Text vvStatusBlockPersona = new Text(By.id("ssmc-dashboard-status-label-volumeStatus"));

    /**
     * This object is used to interact with the <b>HostSet Text Block Persona Status panel</b> of Dashboard.
     */
    public final Text hostSetStatusBlockPersona = new Text(By.id("ssmc-dashboard-status-label-hostSetStatus"));

    /**
     * This object is used to interact with the <b>VVSet Text Block Persona Status panel</b> of Dashboard.
     */
    public final Text vvSetStatusBlockPersona = new Text(By.id("ssmc-dashboard-status-label-volumeSetStatus"));

    /**
     * This object is used to interact with the <b>CPG Text Block Persona Status panel</b> of Dashboard.
     */
    public final Text cpgStatusBlockPersona = new Text(By.id("ssmc-dashboard-status-label-cpgStatus"));

    /**
     * This object is used to interact with the <b>NL allocated Raw capacity panel</b> of dashboard panel
     */
    public final Text nlAllocatedRawCapacity = new Text(By.cssSelector("#ssmc-dashboard-rawcapacityUsage-NL-legend  li.hp-utilization-value > span.hp-value"));

    /**
     * This object is used to interact with the <b>NL free Raw capacity panel</b> of dashboard panel
     */
    public final Text nlFreeRawCapacity = new Text(By.cssSelector("#ssmc-dashboard-rawcapacityUsage-NL-legend  li.hp-utilization-total.hp-utilization-free > span.hp-value"));

    /**
     * This object is used to interact with the <b>NL total Raw capacity panel</b> of dashboard panel
     */
    public final Text nlTotalRawCapacity = new Text(By.id("ssmc-dashboard-rawcapacityUsage-NL-value"));

    /**
     * This object is used to interact with the <b>Total allocated Raw capacity panel</b> of dashboard panel
     */
    public final Text totalAllocatedRawCapacity = new Text(By.cssSelector("#ssmc-dashboard-rawcapacityUsage-Total-legend  li.hp-utilization-value > span.hp-value"));

    /**
     * This object is used to interact with the <b>Total free Raw capacity panel</b> of dashboard panel
     */
    public final Text totalFreeRawCapacity = new Text(By.cssSelector("#ssmc-dashboard-rawcapacityUsage-Total-legend  li.hp-utilization-total.hp-utilization-free > span.hp-value"));

    /**
     * This object is used to interact with the <b>Total Raw capacity panel</b> of dashboard panel
     */
    public final Text totalRawCapacity = new Text(By.id("ssmc-dashboard-rawcapacityUsage-Total-value"));


    /**
     * This object is used to interact with the <b>Activity Panel warning count</b> on the Dashboard.
     */
    public final TextLink warningActivityPanelCount = new TextLink(By.cssSelector("span[data-name='warningAlertCount']"));

    /**
     * This object is used to interact with the <b>Activity Panel< error count/b> on the Dashboard.
     */
    public final TextLink errorActivityPanelCount = new TextLink(By.cssSelector("span[data-name='criticalAlertCount']"));

    /**
     * This object is used to interact with the <b>Total Capacity Panel Allocated label</b> label shown on the Dashboard.
     */
    public final Text totalCapacityAllocatedSingleSystem = new Text(By.cssSelector("label[id$='donut-alloc-tableLabel']"));

    /**
     * This object is used to interact with the <b>Total Capacity Panel free Capacity label</b> label shown on the Dashboard.
     */
    public final Text totalCapacityFreeSingleSystem = new Text(By.cssSelector("label[id$='donut-free-tableLabel']"));

    /**
     * This object is used to interact with the <b>Total Capacity Panel Allocated data</b> label shown on the Dashboard.
     */
    public final Text totalCapacityAllocatedDataSingleSystem = new Text(By.cssSelector("label[id$='-donut-alloc']"));

    /**
     * This object is used to interact with the <b>Total Capacity Panel free Capacity data</b> label shown on the Dashboard.
     */
    public final Text totalCapacityFreeDataSingleSystem = new Text(By.cssSelector("label[id$='-donut-free']"));

    /**
     * This object is used to interact with the <b>Total Capacity Panel total Capacity data</b> label shown on the Dashboard.
     */
    public final Text totalCapacityTotalDataSingleSystem = new Text(By.cssSelector("label[id$='donut-total']"));

    /**
     * This object is used to interact with the <b>Dashboard</b> label shown on the Dashboard.
     */
    public final Text dashboardLabel = new Text(By.cssSelector("#ssmc-customdashboard-page  h1"));
}
