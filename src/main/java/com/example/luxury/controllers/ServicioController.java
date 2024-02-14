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



import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.IOException;
import com.example.luxury.models.services.UploadService;





import com.example.luxury.appdata.AppData;
import com.example.luxury.models.entities.Servicio;
import com.example.luxury.models.services.ServicioService;




@Controller
@SessionAttributes("servicio")
@RequestMapping("/servicios")
public class ServicioController {

	private final AppData appData;
	private final ServicioService servicioService;
	
	
	
	
	
	private final UploadService uploadService;

		
	public static final String OPGEN = "SERVICIOS"; 
	
	public ServicioController(UploadService uploadService,
										 
										 
									     ServicioService servicioService,
									     AppData applicationData
		   
		   		 
			) {
		this.appData = applicationData;
		this.servicioService = servicioService;
		
		
		

		this.uploadService = uploadService;

	}

		
	
	@GetMapping({ "", "/", "/list", "/list/{page}" })
	public String list(@PathVariable(name = "page", required = false) Integer page, Model model) {
	
		if (page == null)
			page = 0;
		
		fillApplicationData(model,"LIST");
		
		Pageable pageRequest = PageRequest.of(page, 10);
		Page<Servicio> pageServicio = servicioService.findAll(pageRequest); 
		PageRender<Servicio> paginator = new PageRender<>("/servicios/list",pageServicio,5);
		

		model.addAttribute("numservicio", servicioService.count());
		model.addAttribute("listservicio", pageServicio);
		model.addAttribute("paginator",paginator);
		
		model.addAttribute("actualpage", page);
		
		return "servicios/list";
	}
	
	@GetMapping({ "/formcr", "/formcr/{page}" })
	public String form(@PathVariable(name = "page", required = false) Integer page, Model model) {
		Servicio servicio = new Servicio();		
		model.addAttribute("servicio",servicio);
		
		if (page == null)
			page = 0;
		model.addAttribute("actualpage", page);
		
		fillApplicationData(model,"CREATE");
		
		return "servicios/form";
	}
	
	@GetMapping({ "/formup/{id}", "/formup/{id}/{page}" })
	public String form(@PathVariable(name = "id") Long id, @PathVariable(name = "page", required = false) Integer page, Model model, RedirectAttributes flash) {
		if (page == null)
			page = 0;
		Servicio servicio = servicioService.findOne(id);
		if(servicio==null) {
			flash.addFlashAttribute("error","Servicio no encontrado");
			return "redirect:/servicio/list/" + page;
		}
		
		model.addAttribute("servicio", servicio);
		
		model.addAttribute("actualpage", page);
		
		fillApplicationData(model,"UPDATE");
		
		return "servicios/form";
	}
	
	
	@PostMapping("/form/{page}")
	@Secured("ROLE_ADMIN")
	public String form(@Valid Servicio servicio,  
			           BindingResult result, 
					   
					   Model model,
					   @RequestAttribute("file") MultipartFile image_formname,
@RequestParam("imageImageText") String imageImageText,
@RequestParam("imageImageTextOld") String imageImageTextOld,

					   @PathVariable(name = "page") int page,
					   RedirectAttributes flash,
					   SessionStatus status) {
		
		boolean creating;
		
		if(servicio.getId()==null) {
			fillApplicationData(model,"CREATE");
			creating = true;
		} else {
			fillApplicationData(model,"UPDATE");
			creating = false;
		}
		
		String msg = (servicio.getId()==null) ? "Servicio dado de alta correctamente" : "Servicio modificado correctamente";
		
		if(result.hasErrors()) {
			model.addAttribute("actualpage", page);
			return "servicios/form";
		}
		
		if(!image_formname.isEmpty())
	AddUpdateImageImage(image_formname,servicio);
else {
	if(imageImageText.isEmpty() && !(imageImageTextOld.isEmpty())) {
		uploadService.delete(imageImageTextOld);
		servicio.setImage(null);
	}
}



		
		
		
		servicioService.save(servicio);
		status.setComplete();
		flash.addFlashAttribute("success",msg);
		
		if (creating)
			page = lastPage();
		
		return "redirect:/servicios/list/" + page;
	}
	
	
	private void AddUpdateImageImage(MultipartFile image, Servicio servicio) {
					
			if(servicio.getId()!=null &&
			   servicio.getId()>0 && 
			   servicio.getImage()!=null &&
			   servicio.getImage().length() > 0) {
			
				uploadService.delete(servicio.getImage());
			}
			
			String uniqueName = null;
			try {
				uniqueName = uploadService.copy(image);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			servicio.setImage(uniqueName);
		
	}

	

	@Secured("ROLE_ADMIN")
	@GetMapping({ "/delete/{id}", "/delete/{id}/{page}" })
	public String delete(@PathVariable(name = "id") Long id,
			@PathVariable(name = "page", required = false) Integer page, RedirectAttributes flash) {
		
		if (page == null)
			page = 0;
		
		if(id>0) { 			
			Servicio servicio = servicioService.findOne(id);
			
			if(servicio != null) {
				
		/* Only if there is required relation with this entity */			
				
		
		/* Only if there is no required relation with this entity, or there is a
		 * required relation but no entity related at the moment, (checked before) */
				
		
		/* Relations revised, the entity can be removed */
				servicioService.remove(id);
			} else {
				flash.addFlashAttribute("error","Servicio no encontrado");
				return "redirect:/servicios/list/" + page;
			}
			
			if(servicio.getImage()!=null)
	uploadService.delete(servicio.getImage());

						
			flash.addFlashAttribute("success","Servicio eliminado correctamente");
		}
		
		return "redirect:/servicios/list/" + page;
	}
	
	@GetMapping({ "/view/{id}", "/view/{id}/{page}" })
	public String view(@PathVariable(name = "id") Long id,
			@PathVariable(name = "page", required = false) Integer page, Model model, RedirectAttributes flash) {

		if (page == null)
			page = 0;
		
		if (id > 0) {
			Servicio servicio = servicioService.findOne(id);

			if (servicio == null) {
				flash.addFlashAttribute("error", "Servicio no encontrado");
				return "redirect:/servicios/list/" + page;
			}

			model.addAttribute("servicio", servicio);
			model.addAttribute("actualpage", page);
			fillApplicationData(model, "VIEW");
			return "servicios/view";
			
		}

		return "redirect:/servicios/list/" + page;
	}
	
	
	@GetMapping("/viewimg/{id}/{imageField}")
	public String viewimg(@PathVariable Long id, @PathVariable String imageField, Model model, RedirectAttributes flash) {

		if (id > 0) {
			Servicio servicio = servicioService.findOne(id);

			if (servicio == null) {
				flash.addFlashAttribute("error", "Servicio no encontrado");
				return "redirect:/servicios/list";
			}

			model.addAttribute("servicio", servicio);
			fillApplicationData(model, "VIEWIMG");
			model.addAttribute("backOption",true);
			model.addAttribute("imageField",imageField);
			
			return "servicios/viewimg";
			
		}

		return "redirect:/servicios/list";
	}
	
	
	
	
	private int lastPage() {
		Long nReg = servicioService.count();
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

