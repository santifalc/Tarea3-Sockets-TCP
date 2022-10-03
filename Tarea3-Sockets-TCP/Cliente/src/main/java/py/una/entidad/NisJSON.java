/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package py.una.entidad;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Santi
 */
public class NisJSON {

    public static String objetoString(Nis n) {
        JSONObject obj = new JSONObject();
        obj.put("id", n.getId());
        obj.put("estado", n.getEstado());
        obj.put("consumo", n.getConsumo());

        return obj.toJSONString();
    }

    public static Nis stringObjeto(String str) throws Exception {
        Nis n = new Nis();
        JSONParser parser = new JSONParser();

        Object obj = parser.parse(str.trim());
        JSONObject jsonObject = (JSONObject) obj;

        Long id = (Long) jsonObject.get("id");
        n.setId(id);
        n.setEstado((Boolean) jsonObject.get("estado"));
        if (jsonObject.get("consumo") != null) {
            n.setConsumo(Integer.parseInt(jsonObject.get("consumo").toString()));
        }

        return n;
    }
}
