package com.ec1.demo;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping(path = "/mascotas")
public class MainController {

    @Autowired
    private MascotaRepository mascotarepository;

    @Autowired
  private JdbcTemplate jdbcTemplate;


    // Mostrar Mascotas 
    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Mascota> getMascotasAll(){
        return mascotarepository.findAll();
    }

    // crear una mascota
    @PostMapping(path = "/add")
    public @ResponseBody String addNewMascota(@RequestParam String nombre, @RequestParam String raza, 
    @RequestParam String propietario){
        Mascota mascota = new Mascota();
        mascota.setNombre(nombre);
        mascota.setRaza(raza);
        mascota.setPropietario(propietario);
        mascotarepository.save(mascota);

        return "Mascota registrada con exito";
    }

    // editar datos de la mascota
    @PutMapping(path = "/edit")
    public @ResponseBody String editMascota(@RequestParam Integer id,
     @RequestParam String nombre, @RequestParam String raza, @RequestParam String propietario){

        Mascota mascota =new Mascota();
        mascota.setId(id);
        mascota.setNombre(nombre);
        mascota.setRaza(raza);
        mascota.setPropietario(propietario);
        mascotarepository.save(mascota);
        return "Dato de la mascota actualizado exitosamente";
    }

    // buscar mascota por id
    @GetMapping(path = "/ver/{id}")
    public @ResponseBody Mascota showMascotaById(@PathVariable Integer id){
        return mascotarepository.findById(id).get();
    }

    // eliminar mascota
    @DeleteMapping(path = "/del")
    public @ResponseBody String deleteMascota(@RequestParam Integer id){
        Mascota mascota = new Mascota();
        mascota.setId(id);
        mascotarepository.delete(mascota);
        return "El registro de la mascota se elimino con exito";
    }

    // visualizar reporte de mascotas
    @GetMapping(path = "/get/report")
    public @ResponseBody List<Map<String,Object>> getReportMascota(){
        String sql="select CONCAT(nombre, '(Nombre) ==> (Propietario)',propietario) as REPORTE_MASCOTAS from mascota";
        List<Map<String, Object>> mascotasReport = jdbcTemplate.queryForList(sql);
        return mascotasReport;
    }
    
}

