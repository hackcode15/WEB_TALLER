package com.app.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.Dto.MechanicDTO;
import com.app.model.Mechanic;
import com.app.model.Role;
import com.app.model.RoleEnum;
import com.app.repo.MechanicRepo;
import com.app.repo.RoleRepo;
import com.app.service.Imp.IMechanicService;

@Service
public class MechanicService implements IMechanicService {

    @Autowired
    private MechanicRepo repo;

    @Autowired
    private RoleRepo roleRepo;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    @Override
    public Mechanic findMechanicById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Error mecanico no encontrado con ID: " + id));    
    }

    @Override
    public List<MechanicDTO> findAllMechanicDTOs() {

        List<MechanicDTO> mechanicDTOs = repo.findAll().stream()
                .map(mechanic -> {
                    MechanicDTO dto = MechanicDTO.builder()
                            .id(mechanic.getId())
                            .firstName(mechanic.getFirstName())
                            .lastName(mechanic.getLastName())
                            .birthdate(mechanic.getBirthdate())
                            .age(mechanic.calcularEdad())
                            .salary(mechanic.getSalary())
                            .specialization(mechanic.getSpecialization())
                            .dateEntry(mechanic.getDateEntry())
                            .build();
                    return dto;
                })
                .collect(Collectors.toList());

        return mechanicDTOs;

    }

    @Override
    public void saveMechanic(MechanicDTO mechanicDTO) {

        Mechanic mechanic;

        if (mechanicDTO.getId() != null) {

            mechanic = repo.findById(mechanicDTO.getId())
                    .orElseThrow(() -> new RuntimeException("Error mecanico no encontrado con id: " + mechanicDTO.getId()));

            mechanic.setUsername(mechanicDTO.getUsername());
            mechanic.setPassword(passwordEncoder.encode(mechanicDTO.getPassword()));
            mechanic.setFirstName(mechanicDTO.getFirstName());
            mechanic.setLastName(mechanicDTO.getLastName());
            mechanic.setBirthdate(mechanicDTO.getBirthdate());
            mechanic.setSalary(mechanicDTO.getSalary());
            mechanic.setSpecialization(mechanicDTO.getSpecialization());
            mechanic.setDateEntry(mechanicDTO.getDateEntry());

        } else {

            Role rolUser = roleRepo.findByRoleEnum(RoleEnum.USER);
            if (rolUser == null) {
                throw new RuntimeException("Rol USER no encontrado");
            }

            mechanic = Mechanic.builder()
                    .username(mechanicDTO.getUsername())
                    .password(passwordEncoder.encode(mechanicDTO.getPassword()))
                    .firstName(mechanicDTO.getFirstName())
                    .lastName(mechanicDTO.getLastName())
                    .birthdate(mechanicDTO.getBirthdate())
                    .salary(mechanicDTO.getSalary())
                    .specialization(mechanicDTO.getSpecialization())
                    .dateEntry(mechanicDTO.getDateEntry())
                    .roles(Set.of(rolUser))
                    .build();

        }

        repo.save(mechanic);

    }

    @Override
    public MechanicDTO findMechanicDtoById(Integer id) {

        Mechanic mechanic = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Error mecanico no existe con id" + id));

        MechanicDTO mechanicDTO = MechanicDTO.builder()
            .id(mechanic.getId())
            .username(mechanic.getUsername())
            .password(mechanic.getPassword())
            .firstName(mechanic.getFirstName())
            .lastName(mechanic.getLastName())
            .birthdate(mechanic.getBirthdate())
            .age(mechanic.calcularEdad())
            .salary(mechanic.getSalary())
            .specialization(mechanic.getSpecialization())
            .dateEntry(mechanic.getDateEntry())
            .build();

        return mechanicDTO;

    }

    @Override
    public void deleteMechanicById(Integer id) {

        Mechanic mechanic = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Error mecanico no encontrado con id: " + id));

        repo.deleteById(mechanic.getId());

    }

    @Override
    public void updateMechanic(Mechanic mechanic) {
        repo.save(mechanic);
    }

}
