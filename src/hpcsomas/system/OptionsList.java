package hpcsomas.system;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

/**
 *
 * @author apcrol
 * Класс списка опций, каждая из которых содержит три параметра : имя, описание, значение
 * 
 */
public class OptionsList {
    private static final Logger logger = LogManager.getLogger(OptionsList.class);
    
     ArrayList<StringTriplet> options;
     
     public OptionsList (){
        try{
            options = new ArrayList<StringTriplet>();
        }catch (Exception ex) {            
            logger.error("Problems with OptionsList constructor : ");
            logger.error(ex);
        }
     }
     /**
     * Загрузка опций из json файла, загружаются только пары имя-описание
     * @param json
     */
     public void LoadOptionsMapJSON(JSONObject json){         
        if (json==null){
            logger.error("Got null - json in LoadOptionsMapJSON. ");
            return;
        }
        try{
            Set keys = json.keySet();
            Iterator a = keys.iterator();
            while(a.hasNext()) {
                String key = (String)a.next();
                String description = (String)json.get(key);
                options.add(new StringTriplet(key, description, ""));
            }
        }catch (Exception ex) {            
            logger.error("Problems with LoadOptionsMapJSON constructor : ");
            logger.error(ex);
        }
     
     }
     /**
     * Установка значения записи, меняются только value
     * @param name - имя записи
     * @param val - значение
     */
     public void SetValue(String name, String val){
        try{
            for (StringTriplet option : options) {
                if(option.getName().equals(name))
                    option.setValue(val);
            }
         }catch (Exception ex) {
             logger.error("Something went wrong whlie using SetValue : " + name + " , "+ val+" " + ex);
        }
     }
     /**
     * Из полученного json собираются пары имя-значение, чтобы заполнить параметры
     * @param json
     */ 
     public void SetValuesJSON(JSONObject json){
        if (json==null){
            logger.error("Got null - json in SetValuesJSON. ");
            return;
        }
        try{
            Set keys = json.keySet();
            Iterator a = keys.iterator();
            while(a.hasNext()) {
                String key = (String)a.next();
                String value = (String)json.get(key);
                this.SetValue(key,value);
            }
        }catch (Exception ex) {
             logger.error("Something went wrong whlie using SetValuesJSON : " + ex);
        }
     }
     /**
     * Формируем html строку из имеющихся данных
     * @return простая строка
     */
     public String GetOptionsListHTML(){
        try{
            String s = "";
            for (StringTriplet option : options) {
                s += "\n" + option.getDescription()+ " : " + option.getValue() + "<br>";
            }
            return s;
        }catch (Exception ex) {
             logger.error("Something went wrong whlie using GetOptionsListHTML : " + ex);
        }
        return null;
     }
}
