package com.app.service.Imp;

import java.util.List;

import com.app.Dto.MechanicDTO;
import com.app.model.Mechanic;

public interface IMechanicService {

    // --
    public void updateMechanic(Mechanic mechanic);

    public List<MechanicDTO> findAllMechanicDTOs();
    public void saveMechanic(MechanicDTO mechanicDTO);
    public MechanicDTO findMechanicDtoById(Integer id);
    public Mechanic findMechanicById(Integer id);
    public void deleteMechanicById(Integer id);

}
