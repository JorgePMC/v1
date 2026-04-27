package com.example.bibliotecaduoc.dto;


public class PokemonResponse {
 
 
    private Long id;
    private String name;

 

    private Boolean is_legendary;
//############################
    private int base_happiness;
 
 
    public Long getId() {
        return id;
    }
 
 
    public void setId(Long id) {
        this.id = id;
    }
 
 
    public String getName() {
        return name;
    }
 
 
    public void setName(String name) {
        this.name = name;
    }
 
 
    public Boolean getIs_legendary() {
        return is_legendary;
    }
 
 
    public void setIs_legendary(Boolean isLegendary) {
        this.is_legendary = isLegendary;
    }
 //#######################################################

   public int getBase_Happiness() {
        return base_happiness;
    }
 
    public void setBase_Happiness(int base_happiness) {
        this.base_happiness = base_happiness;
    }
}
