package gr.hua.dit.petapp.services;

import gr.hua.dit.petapp.entities.MedicalHistory;
import gr.hua.dit.petapp.entities.Pet;
import gr.hua.dit.petapp.repositories.MedicalHistoryRepository;
import gr.hua.dit.petapp.repositories.PetRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalHistoryService
{
    private MedicalHistoryRepository medicalHistoryRepository;
    private PetRepository petRepository;

    public MedicalHistoryService(MedicalHistoryRepository medicalHistoryRepository, PetRepository petRepository)
    {
        this.medicalHistoryRepository = medicalHistoryRepository;
        this.petRepository = petRepository;
    }

    @Transactional
    public List<MedicalHistory> getMedicalHistories()
    {
        return medicalHistoryRepository.findAll();
    }

    @Transactional
    public MedicalHistory getMedicalHistory(Long id)
    {
        return medicalHistoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Medical history not found"));
    }

    @Transactional
    public MedicalHistory getMedicalHistoryByPetId(Long id)
    {
        Pet pet = petRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Pet not found"));
        List<MedicalHistory> medicalHistories = getMedicalHistories();

        for(MedicalHistory medicalHistory : medicalHistories)
        {
            if(medicalHistory.getPet().getID() == pet.getID())
            {
                return medicalHistory;
            }
        }

        throw new IllegalArgumentException("Medical history not found");
    }

    @Transactional
    public void saveMedicalHistory(MedicalHistory medicalHistory)
    {
        Pet pet = medicalHistory.getPet();
        System.out.println(pet.toString());
        medicalHistory.setPet(pet);
        medicalHistoryRepository.save(medicalHistory);
    }

    @Transactional
    public void updateMedicalHistory(MedicalHistory medicalHistory)
    {
        Long id = medicalHistory.getID();
        MedicalHistory medical = medicalHistoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Pet not found"));
        medical.setHealthStatus(medicalHistory.getHealthStatus());
        medicalHistoryRepository.save(medical);
    }

    @Transactional
    public void deleteMedicalHistory(Long id)
    {
        MedicalHistory medicalHistory = medicalHistoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Medical history not found"));
        medicalHistoryRepository.deleteById(id);
    }

    @Transactional
    public void saveMedicalHistory(Long id, MedicalHistory medicalHistory)
    {
        Pet pet = petRepository.findById(id).get();
        medicalHistory.setPet(pet);
        medicalHistoryRepository.save(medicalHistory);

    }
}
