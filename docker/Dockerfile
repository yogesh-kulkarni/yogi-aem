FROM java

RUN mkdir /opt/aem/

WORKDIR /opt/aem/

ADD cq-author-4502.jar /opt/aem/cq-quickstart-6.0.jar
ADD license.properties /opt/aem/license.properties

RUN java -jar cq-quickstart-6.0.jar -unpack -v

ENV CQ_FOREGROUND y
ENV CQ_VERBOSE    y
ENV CQ_NOBROWSER  y
ENV CQ_RUNMODE    "dev,author,nosamplecontent"
ENV CQ_JVM_OPTS   "-server -Xmx1524M -Xms512M -XX:MaxPermSize=512M"



CMD crx-quickstart/bin/quickstart
EXPOSE 4502 8000
