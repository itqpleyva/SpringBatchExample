package MainPackege.Batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import MainPackege.Repository.DinosaurioRepository;
import MainPackege.model.Dinosaurio;

@Component
public class Writer implements ItemWriter<Dinosaurio> {

	@Autowired
	DinosaurioRepository dinosaurioRepository;
	
	@Override
	public void write(List<? extends Dinosaurio> dinos) throws Exception {
		
		System.out.println("saving dinos");
		dinosaurioRepository.saveAll(dinos);
	}

}
