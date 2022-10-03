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
public class Nis extends NisJSON{

    private Long id;
    private Boolean estado;
    private Integer consumo;

    public Nis() {
    }

    public Nis(Long id, Boolean estado, Integer consumo) {
        this.id = id;
        this.estado = estado;
        this.consumo = consumo;
    }
    
    public Nis(String str) throws Exception {
        JSONParser parser = new JSONParser();

        Object obj = parser.parse(str.trim());
        JSONObject jsonObject = (JSONObject) obj;
        
        this.id = (Long) jsonObject.get("id");
        this.estado = (Boolean) jsonObject.get("estado");
        this.consumo = Integer.parseInt(jsonObject.get("consumo").toString());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Integer getConsumo() {
        return consumo;
    }

    public void setConsumo(Integer consumo) {
        this.consumo = consumo;
    }

    @Override
    public String toString() {
        return "Nis{" + "id=" + id + ", estado=" + estado + ", consumo=" + consumo + '}';
    }
        
}
