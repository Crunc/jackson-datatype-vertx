# jackson-datatype-vertx
Jackson2 Module which allows reading/writing org.vertx.java.core.json.JsonObject

## Usage

    ObjectMapper om = new ObjectMapper();
    om.registerModule(new VertxJsonModule());
    
    // ...
    
    JsonObject object = om.readValue("{}", JsonObject.class);

    // ...

    String jsonObject = om.writeValueAsString(new JsonObject());

    // ...

    JsonArray object = om.readValue("[]", JsonArray.class);

    // ...

    String jsonArray = om.writeValueAsString(new JsonArray());
