FROM 218894879100.dkr.ecr.us-east-1.amazonaws.com/corretto-17-jdk-debian:latest

ARG VERSION
ENV JAVA_OPTS=-Xmx1G

ENV JMX_OPTS="\
    -Dcom.sun.management.jmxremote.port=9016 \
    -Dcom.sun.management.jmxremote.rmi.port=9016 \
    -Dcom.sun.management.jmxremote.local.only=false \
    -Dcom.sun.management.jmxremote.ssl=false \
    -Dcom.sun.management.jmxremote.authenticate=false"

ENV DEFAULT_OPTS "-Dconfig.override_with_env_vars=true \
                  -Dvertx.logger-delegate-factory-class-name=io.vertx.core.logging.SLF4JLogDelegateFactory \
                  -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"

ENV VERTICLE_FILE java-template-service-*-fat.jar

# Set the location of the verticles
ENV VERTICLE_HOME /usr/verticles

EXPOSE 8080
EXPOSE 5005
EXPOSE 9016

# Copy your fat jar to the container
COPY build/libs/$VERTICLE_FILE $VERTICLE_HOME/lib/

# Launch the verticle
WORKDIR $VERTICLE_HOME
ENTRYPOINT ["sh", "-c"]
# Overwrite the local port value of the server with the default port value
ENV CONFIG_FORCE_service_server_port 8080
CMD ["exec java ${JAVA_OPTS} ${JMX_OPTS} ${DEFAULT_OPTS} -jar lib/${VERTICLE_FILE}"]
