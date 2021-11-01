## Example of Testbench in Migration Projects  

Here we have an example migration project, where a random Vaadin 8 Application should be migrated to Vaadin 14. The Vaadin 8 Application has been tested with Vaadin Testbench. This application is now to be migrated with Multi Plattform Runtime and the testcases implemented in Vaadin 8 should be further used to ensure its correct use.

To use the legacy component in the new Vaadin 14 application, the Vaadin 8 application is splitted into two modules to separate the components into a jar dependency and the runtime elements into a war dependency. The Vaadin 14 Application is also put into a single module and import the Vaadin 8 module to use the legacy component and wrap them into a Vaadin 14 application.

If we want to test the application we need to call ``mvn verify -Pit``. The chrome driver will be downloaded via ``io.github.bonigarcia.webdrivermanager`` and the legacy testcases as well as the new testcases are executed from each application module (vaadin8-war-app & vaadin14-mpr-app).

To execute the project you need a Vaadin Pro license which can be used as a trial in the first instance. Sign up on vaadin.com to get a trial license.