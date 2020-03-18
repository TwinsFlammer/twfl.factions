package com.redecommunity.factions.permission.enums;

import com.redecommunity.api.spigot.inventory.item.CustomItem;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
@Getter
public enum PermissionType {
    CONSTRUCT(
            "construir no terreno",
            new String[]{
                    "§7Permite o jogador colocar e quebrar",
                    "§7qualquer bloco dentro de suas terras.",
                    "",
                    "§7Estado: {state}",
                    "",
                    "§7Clique para {state_parent} esta permissão."
            },
            new CustomItem(Material.WOOD_PICKAXE)
                    .name("§aConstruir no terreno")
    ),
    CHEST_ACCESS(
            "acessar containers",
            new String[]{
                    "§7Permite o jogador abrir baús, fornalhas,",
                    "§7funis, liberadores e ejetores dentro de",
                    "§7suas terras.",
                    "",
                    "§7Estado: {state}",
                    "",
                    "§7Clique para {state_parent} esta permissão."
            },
            new CustomItem(Material.MINECART)
                    .name("§aAcessar containers")
    ),
    CLAIM(
            "dominar terrenos",
            new String[]{
                    "§7Permite o jogador dominar novas terras",
                    "§7para sua facção. Isso inclui proteger",
                    "§7terrenos através do /f proteger.",
                    "",
                    "§7Estado: {state}",
                    "",
                    "§7Clique para {state_parent} esta permissão."
            },
            new CustomItem(Material.GRASS)
                    .name("§aDominar terrenos")
    ),
    UNCLAIM(
            "abandonar terrenos",
            new String[]{
                    "§7Permite o jogador abandonar qualquer",
                    "§7terreno de sua facção.",
                    "",
                    "§7Estado: {state}",
                    "",
                    "§7Clique para {state_parent} esta permissão."
            },
            new CustomItem(Material.DIRT)
                    .data(1)
                    .name("§aAbandonar terrenos")
    ),
    INVITE(
            "recrutar membros",
            new String[]{
                    "§7Permite que o jogador convide novos jogadores",
                    "§7para fazerem parte de sua facção.",
                    "",
                    "§7Estado: {state}",
                    "",
                    "§7Clique para {state_parent} esta permissão."
            },
            new CustomItem(Material.RED_MUSHROOM)
                    .name("§aRecrutar membros")
    ),
    KICK(
            "expulsar membros",
            new String[]{
                    "§7Permite que o jogador expulse os",
                    "§7membros de sua facção que não",
                    "§7possuem essa permissão.",
                    "",
                    "§7Estado: {state}",
                    "",
                    "§7Clique para {state_parent} esta permissão."
            },
            new CustomItem(Material.BROWN_MUSHROOM)
                    .name("§aExpulsar membros")
    ),
    REDSTONE_USE(
            "utilizar redstone",
            new String[]{
                    "§7Permite que o jogador utilize botões, alavancas,",
                    "§7portas, placas de pressão, alçapões, portões,",
                    "§7repetidores e comparadores em suas terras.",
                    "",
                    "§7Estado: {state}",
                    "",
                    "§7Clique para {state_parent} esta permissão."
            },
            new CustomItem(Material.MINECART)
                    .name("§aUtilizar redstone")
    ),
    BEACON_ACCESS(
            "acessar sinalizadores",
            new String[]{
                    "§7Permite que o jogador tenha acesso aos",
                    "§7sinalizadores colocados em suas terras.",
                    "",
                    "§7Estado: {state}",
                    "",
                    "§7Clique para {state_parent} esta permissão."
            },
            new CustomItem(Material.MINECART)
                    .name("§aAcessar sinalizadores")
    ),
    GENERATOR_ACCESS(
            "retirar geradores",
            new String[]{
                    "§7Permite que o jogador retire os geradores",
                    "§7armazenados no /f geradores de sua facção.",
                    "",
                    "§7Estado: {state}",
                    "",
                    "§7Clique para {state_parent} esta permissão."
            },
            null
    ),
    HOME_ACCESS(
            "acessar a base da facção",
            new String[]{
                    "§7Permite que o jogador tenha acesso",
                    "§7a base da sua facção",
                    "",
                    "§7Estado: {state}",
                    "",
                    "§7Clique para {state_parent} esta permissão."
            },
            new CustomItem(Material.MINECART)
                    .name("§aAcessar a base da facção")
    ),
    TERRAIN_ACCESS(
            "ir aos terrenos da facção",
            new String[]{
                    "§7Permite o jogador se teletransportar para homes",
                    "§7criadas nas terras de sua facção. Também permite",
                    "§7que o jogador defina novas homes em suas terras.",
                    "",
                    "§7Estado: {state}",
                    "",
                    "§7Clique para {state_parent} esta permissão."
            },
            new CustomItem(Material.MINECART)
                    .name("§aIr aos terrenos da facção")
    ),
    TELEPORT_REQUEST_ACCEPT(
            "aceitar pedidos de tpa",
            new String[]{
                    "§7Permite que o jogador aceite pedidos de",
                    "§7teletransporte de jogadores que não façam",
                    "§7parte de sua facção dentro de suas terras.",
                    "",
                    "§7Estado: {state}",
                    "",
                    "§7Clique para {state_parent} esta permissão."
            },
            new CustomItem(Material.MINECART)
                    .name("§aAceitar pedidos de tpa")
    );

    private final String name;
    private final String[] lore;
    private final CustomItem customItem;
}