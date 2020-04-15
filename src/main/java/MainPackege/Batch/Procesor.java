package MainPackege.Batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import MainPackege.model.Dinosaurio;

@Component
public class Procesor implements ItemProcessor<Dinosaurio, Dinosaurio> {

	public Procesor() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Dinosaurio process(Dinosaurio dino) throws Exception {
		
		if (dino.getDescription().equals("extinto")) {
			System.out.println(dino.getCategory());
			dino.setDescription("Extintos años atrás");
		}

		System.out.println("Processing dinos");
		return dino;
	}
 
	

}
