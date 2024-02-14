package com.example.luxury.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.luxury.util.paginator.PageRender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.validation.BindingResult;
import javax.validation.Valid;
import org.springframework.web.bind.support.SessionStatus;








import com.example.luxury.appdata.AppData;
import com.example.luxury.models.entities.Reserva;
import com.example.luxury.models.services.ReservaService;




@Controller
@SessionAttributes("reserva")
@RequestMapping("/reservas")
public class ReservaController {

	private final AppData appData;
	private final ReservaService reservaService;
	
	
	
	
	
	
		
	public static final String OPGEN = "RESERVAS"; 
	
	public ReservaController(
										 
										 
									     ReservaService reservaService,
									     AppData applicationData
		   
		   		 
			) {
		this.appData = applicationData;
		this.reservaService = reservaService;
		
		
		

		
	}

		
	
	@GetMapping({ "", "/", "/list", "/list/{page}" })
	public String list(@PathVariable(name = "page", required = false) Integer page, Model model) {
	
		if (page == null)
			page = 0;
		
		fillApplicationData(model,"LIST");
		
		Pageable pageRequest = PageRequest.of(page, 10);
		Page<Reserva> pageReserva = reservaService.findAll(pageRequest); 
		PageRender<Reserva> paginator = new PageRender<>("/reservas/list",pageReserva,5);
		

		model.addAttribute("numreserva", reservaService.count());
		model.addAttribute("listreserva", pageReserva);
		model.addAttribute("paginator",paginator);
		
		model.addAttribute("actualpage", page);
		
		return "reservas/list";
	}
	
	@GetMapping({ "/formcr", "/formcr/{page}" })
	public String form(@PathVariable(name = "page", required = false) Integer page, Model model) {
		Reserva reserva = new Reserva();		
		model.addAttribute("reserva",reserva);
		
		if (page == null)
			page = 0;
		model.addAttribute("actualpage", page);
		
		fillApplicationData(model,"CREATE");
		
		return "reservas/form";
	}
	
	@GetMapping({ "/formup/{id}", "/formup/{id}/{page}" })
	public String form(@PathVariable(name = "id") Long id, @PathVariable(name = "page", required = false) Integer page, Model model, RedirectAttributes flash) {
		if (page == null)
			page = 0;
		Reserva reserva = reservaService.findOne(id);
		if(reserva==null) {
			flash.addFlashAttribute("error","Reserva no encontrada");
			return "redirect:/reserva/list/" + page;
		}
		
		model.addAttribute("reserva", reserva);
		
		model.addAttribute("actualpage", page);
		
		fillApplicationData(model,"UPDATE");
		
		return "reservas/form";
	}
	
	
	@PostMapping("/form/{page}")
	@Secured("ROLE_ADMIN")
	public String form(@Valid Reserva reserva,  
			           BindingResult result, 
					   
					   Model model,
					   
					   @PathVariable(name = "page") int page,
					   RedirectAttributes flash,
					   SessionStatus status) {
		
		boolean creating;
		
		if(reserva.getId()==null) {
			fillApplicationData(model,"CREATE");
			creating = true;
		} else {
			fillApplicationData(model,"UPDATE");
			creating = false;
		}
		
		String msg = (reserva.getId()==null) ? "Reserva dada de alta correctamente" : "Reserva modificada correctamente";
		
		if(result.hasErrors()) {
			model.addAttribute("actualpage", page);
			return "reservas/form";
		}
		
		
		
		
		
		reservaService.save(reserva);
		status.setComplete();
		flash.addFlashAttribute("success",msg);
		
		if (creating)
			page = lastPage();
		
		return "redirect:/reservas/list/" + page;
	}
	
	
	
	

	@Secured("ROLE_ADMIN")
	@GetMapping({ "/delete/{id}", "/delete/{id}/{page}" })
	public String delete(@PathVariable(name = "id") Long id,
			@PathVariable(name = "page", required = false) Integer page, RedirectAttributes flash) {
		
		if (page == null)
			page = 0;
		
		if(id>0) { 			
			Reserva reserva = reservaService.findOne(id);
			
			if(reserva != null) {
				
		/* Only if there is required relation with this entity */			
				
		
		/* Only if there is no required relation with this entity, or there is a
		 * required relation but no entity related at the moment, (checked before) */
				
		
		/* Relations revised, the entity can be removed */
				reservaService.remove(id);
			} else {
				flash.addFlashAttribute("error","Reserva no encontrada");
				return "redirect:/reservas/list/" + page;
			}
			
			
						
			flash.addFlashAttribute("success","Reserva eliminada correctamente");
		}
		
		return "redirect:/reservas/list/" + page;
	}
	
	@GetMapping({ "/view/{id}", "/view/{id}/{page}" })
	public String view(@PathVariable(name = "id") Long id,
			@PathVariable(name = "page", required = false) Integer page, Model model, RedirectAttributes flash) {

		if (page == null)
			page = 0;
		
		if (id > 0) {
			Reserva reserva = reservaService.findOne(id);

			if (reserva == null) {
				flash.addFlashAttribute("error", "Reserva no encontrada");
				return "redirect:/reservas/list/" + page;
			}

			model.addAttribute("reserva", reserva);
			model.addAttribute("actualpage", page);
			fillApplicationData(model, "VIEW");
			return "reservas/view";
			
		}

		return "redirect:/reservas/list/" + page;
	}
	
	
	@GetMapping("/viewimg/{id}/{imageField}")
	public String viewimg(@PathVariable Long id, @PathVariable String imageField, Model model, RedirectAttributes flash) {

		if (id > 0) {
			Reserva reserva = reservaService.findOne(id);

			if (reserva == null) {
				flash.addFlashAttribute("error", "Reserva no encontrada");
				return "redirect:/reservas/list";
			}

			model.addAttribute("reserva", reserva);
			fillApplicationData(model, "VIEWIMG");
			model.addAttribute("backOption",true);
			model.addAttribute("imageField",imageField);
			
			return "reservas/viewimg";
			
		}

		return "redirect:/reservas/list";
	}
	
	
	
	
	private int lastPage() {
		Long nReg = reservaService.count();
		int nPag = (int) (nReg / 10);
		if (nReg % 10 == 0)
			nPag--;
		return nPag;
	}
	
	private void fillApplicationData(Model model, String screen) {
		model.addAttribute("applicationData",appData);
		model.addAttribute("optionCode",OPGEN);
		model.addAttribute("screen",screen);
	}
	
		
}



/*** Duende Code Generator Jose Manuel Rosado ejerciciosmesa.com 2023 ***/

