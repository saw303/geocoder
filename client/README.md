# Run instructions

Make sure you run this application on Java 9. To verify your Java
version open a console and enter `java --version`.

You should get something like this:

    java 9.0.4
    Java(TM) SE Runtime Environment (build 9.0.4+11)
    Java HotSpot(TM) 64-Bit Server VM (build 9.0.4+11, mixed mode)


## Run the client

Open a console and run the following command:

    java -jar \
        --add-modules jdk.incubator.httpclient \
        <path>/geocoder-0.0.1-all.jar \
        <Google API KEY>
        
        
### Run the client on a specific postal code`

You can run the client for a specific postal code. This requires a client version of at least `0.0.6.

    java -jar \
        -Dgeocode.zip=6979 \
        --add-modules jdk.incubator.httpclient \
        <path>/geocoder-0.0.6-all.jar \
        <Google API KEY>
    

