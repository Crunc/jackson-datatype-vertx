# jackson-datatype-vertx
Jackson2 Module which allows reading/writing org.vertx.java.core.json.JsonObject

## Installation

### Maven

    <repositories>
        <repository>
            <id>ossrh</id>
            <url>https://oss.sonatype.org/content/repositories/snapshots</url>
            <snapshots>
                <enabled>true</enabled>
                <updatePolicy>always</updatePolicy>
            </snapshots>
        </repository>
    </repositories>
	
    <dependency>
        <groupId>de.crunc</groupId>
        <artifactId>jackson-datatype-vertx</artifactId>
        <version>2.1-SNAPSHOT</version>
    </dependency>
    
## Usage

### Generation (Custom type -> JsonElement)

    ObjectMapper mapper = new ObjectMapper();
    ObjectMarshaller marshaller = new ObjectMarshaller(mapper);

    SamplePojo pojo = new SamplePojo("Foobar");

    JsonObject json = marshaller.marshall(pojo);
    
### Parsing (JsonElement -> Custom type)

    ObjectMapper mapper = new ObjectMapper();
    ObjectMarshaller marshaller = new ObjectMarshaller(mapper);

    JsonObject json = new JsonObject();
    json.putString("message", "Hello pojo");

    SamplePojo pojo = marshaller.unmarshall(json, SamplePojo.class);

### Serialization (JsonElement -> String)

    ObjectMapper mapper = new ObjectMapper();
    om.registerModule(new VertxJsonModule());

    String jsonObject = mapper.writeValueAsString(new JsonObject());

    String jsonArray = mapper.writeValueAsString(new JsonArray());

### Deserialization (String -> JsonElement)

    ObjectMapper mapper = new ObjectMapper();
    om.registerModule(new VertxJsonModule());
    
    JsonObject object = mapper.readValue("{}", JsonObject.class);

    JsonArray object = mapper.readValue("[]", JsonArray.class);
