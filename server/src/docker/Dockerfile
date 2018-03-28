FROM anapsix/alpine-java:9

VOLUME /tmp
ADD server-0.0.1.jar app.jar
ADD run.sh run.sh

RUN chmod 777 ./run.sh

ENTRYPOINT ["./run.sh"]
