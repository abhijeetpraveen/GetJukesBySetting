# Welcome to the GetJukesBySetting Application

This is a REST API with a single GET endpoint which returns a paginated list of jukeboxes that support a given setting id. It supports the following query parameters:

 - `settingId` - This is a **required** parameter which represents the ID of the setting for which you wish to find all supporting Jukes
 - `model` - This is an **optional** parameter that allows you to filter the Jukes based on their model
 - `offset` - This is an **optional** parameter that allows you to set the index where the page will start
 - `limit` - This is an **optional** parameter that allows you to set the size of the page
### How to run this project 

#### Option 1:
To use the project, clone this repository and run the java file [GetJukesBySettingApplication.java](https://github.com/abhijeetpraveen/GetJukesBySetting/blob/main/src/main/java/abhijeetpraveen/GetJukesBySetting/GetJukesBySettingApplication.java)  \
locally as a  `Spring Boot Application`.

#### Option 2:
The Application has been Dockerized. Therefore, running the following command in terminal will also allow you to use the Application without needing to clone this repositsory. 

```bash
docker run -d -p port {local port of your choice}:{8080} abhijeetpraveen/get_jukes_by_setting_rest_application:1.0.0
```

The Docker Hub Repository can be found [here](https://hub.docker.com/repository/docker/abhijeetpraveen/get_jukes_by_setting_rest_application).

### Using the Application

You can now use the application by entering the following URL in your browser.

```bash
localhost:8080/supportedJukes?id={settingID}&model={model}&offset={offset}&limit={limit}
```

#### The following are error codes or messages that the application may throw
<pre>
404 - When your input Setting or your input Model cannot be found
406 - When any of your inputs is/are invalid
</pre>
These messages are shown in more detail when you use the application but are here for your reference

### The Application has also been tested with multiple unit tests
These tests can be found in the java file [GetJukesBySettingApplicationTests.java](https://github.com/abhijeetpraveen/GetJukesBySetting/blob/main/src/test/java/abhijeetpraveen/GetJukesBySetting/GetJukesBySettingApplicationTests.java)
