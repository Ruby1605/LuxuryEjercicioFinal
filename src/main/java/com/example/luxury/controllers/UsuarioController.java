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
import com.example.luxury.models.entities.Usuario;
import com.example.luxury.models.services.UsuarioService;




@Controller
@SessionAttributes("usuario")
@RequestMapping("/usuarios")
public class UsuarioController {

	private final AppData appData;
	private final UsuarioService usuarioService;
	
	
	
	
	
	private final UploadService uploadService;

		
	public static final String OPGEN = "USUARIOS"; 
	
	public UsuarioController(UploadService uploadService,
										 
										 
									     UsuarioService usuarioService,
									     AppData applicationData
		   
		   		 
			) {
		this.appData = applicationData;
		this.usuarioService = usuarioService;
		
		
		

		this.uploadService = uploadService;

	}

		
	
	@GetMapping({ "", "/", "/list", "/list/{page}" })
	public String list(@PathVariable(name = "page", required = false) Integer page, Model model) {
	
		if (page == null)
			page = 0;
		
		fillApplicationData(model,"LIST");
		
		Pageable pageRequest = PageRequest.of(page, 10);
		Page<Usuario> pageUsuario = usuarioService.findAll(pageRequest); 
		PageRender<Usuario> paginator = new PageRender<>("/usuarios/list",pageUsuario,5);
		

		model.addAttribute("numusuario", usuarioService.count());
		model.addAttribute("listusuario", pageUsuario);
		model.addAttribute("paginator",paginator);
		
		model.addAttribute("actualpage", page);
		
		return "usuarios/list";
	}
	
	@GetMapping({ "/formcr", "/formcr/{page}" })
	public String form(@PathVariable(name = "page", required = false) Integer page, Model model) {
		Usuario usuario = new Usuario();		
		model.addAttribute("usuario",usuario);
		
		if (page == null)
			page = 0;
		model.addAttribute("actualpage", page);
		
		fillApplicationData(model,"CREATE");
		
		return "usuarios/form";
	}
	
	@GetMapping({ "/formup/{id}", "/formup/{id}/{page}" })
	public String form(@PathVariable(name = "id") Long id, @PathVariable(name = "page", required = false) Integer page, Model model, RedirectAttributes flash) {
		if (page == null)
			page = 0;
		Usuario usuario = usuarioService.findOne(id);
		if(usuario==null) {
			flash.addFlashAttribute("error","Usuario no encontrado");
			return "redirect:/usuario/list/" + page;
		}
		
		model.addAttribute("usuario", usuario);
		
		model.addAttribute("actualpage", page);
		
		fillApplicationData(model,"UPDATE");
		
		return "usuarios/form";
	}
	
	
	@PostMapping("/form/{page}")
	@Secured("ROLE_ADMIN")
	public String form(@Valid Usuario usuario,  
			           BindingResult result, 
					   
					   Model model,
					   @RequestAttribute("file") MultipartFile foto_formname,
@RequestParam("fotoImageText") String fotoImageText,
@RequestParam("fotoImageTextOld") String fotoImageTextOld,

					   @PathVariable(name = "page") int page,
					   RedirectAttributes flash,
					   SessionStatus status) {
		
		boolean creating;
		
		if(usuario.getId()==null) {
			fillApplicationData(model,"CREATE");
			creating = true;
		} else {
			fillApplicationData(model,"UPDATE");
			creating = false;
		}
		
		String msg = (usuario.getId()==null) ? "Usuario dado de alta correctamente" : "Usuario modificado correctamente";
		
		if(result.hasErrors()) {
			model.addAttribute("actualpage", page);
			return "usuarios/form";
		}
		
		if(!foto_formname.isEmpty())
	AddUpdateImageFoto(foto_formname,usuario);
else {
	if(fotoImageText.isEmpty() && !(fotoImageTextOld.isEmpty())) {
		uploadService.delete(fotoImageTextOld);
		usuario.setFoto(null);
	}
}



		
		
		
		usuarioService.save(usuario);
		status.setComplete();
		flash.addFlashAttribute("success",msg);
		
		if (creating)
			page = lastPage();
		
		return "redirect:/usuarios/list/" + page;
	}
	
	
	private void AddUpdateImageFoto(MultipartFile image, Usuario usuario) {
					
			if(usuario.getId()!=null &&
			   usuario.getId()>0 && 
			   usuario.getFoto()!=null &&
			   usuario.getFoto().length() > 0) {
			
				uploadService.delete(usuario.getFoto());
			}
			
			String uniqueName = null;
			try {
				uniqueName = uploadService.copy(image);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			usuario.setFoto(uniqueName);
		
	}

	

	@Secured("ROLE_ADMIN")
	@GetMapping({ "/delete/{id}", "/delete/{id}/{page}" })
	public String delete(@PathVariable(name = "id") Long id,
			@PathVariable(name = "page", required = false) Integer page, RedirectAttributes flash) {
		
		if (page == null)
			page = 0;
		
		if(id>0) { 			
			Usuario usuario = usuarioService.findOne(id);
			
			if(usuario != null) {
				
		/* Only if there is required relation with this entity */			
				
		
		/* Only if there is no required relation with this entity, or there is a
		 * required relation but no entity related at the moment, (checked before) */
				
		
		/* Relations revised, the entity can be removed */
				usuarioService.remove(id);
			} else {
				flash.addFlashAttribute("error","Usuario no encontrado");
				return "redirect:/usuarios/list/" + page;
			}
			
			if(usuario.getFoto()!=null)
	uploadService.delete(usuario.getFoto());

						
			flash.addFlashAttribute("success","Usuario eliminado correctamente");
		}
		
		return "redirect:/usuarios/list/" + page;
	}
	
	@GetMapping({ "/view/{id}", "/view/{id}/{page}" })
	public String view(@PathVariable(name = "id") Long id,
			@PathVariable(name = "page", required = false) Integer page, Model model, RedirectAttributes flash) {

		if (page == null)
			page = 0;
		
		if (id > 0) {
			Usuario usuario = usuarioService.findOne(id);

			if (usuario == null) {
				flash.addFlashAttribute("error", "Usuario no encontrado");
				return "redirect:/usuarios/list/" + page;
			}

			model.addAttribute("usuario", usuario);
			model.addAttribute("actualpage", page);
			fillApplicationData(model, "VIEW");
			return "usuarios/view";
			
		}

		return "redirect:/usuarios/list/" + page;
	}
	
	
	@GetMapping("/viewimg/{id}/{imageField}")
	public String viewimg(@PathVariable Long id, @PathVariable String imageField, Model model, RedirectAttributes flash) {

		if (id > 0) {
			Usuario usuario = usuarioService.findOne(id);

			if (usuario == null) {
				flash.addFlashAttribute("error", "Usuario no encontrado");
				return "redirect:/usuarios/list";
			}

			model.addAttribute("usuario", usuario);
			fillApplicationData(model, "VIEWIMG");
			model.addAttribute("backOption",true);
			model.addAttribute("imageField",imageField);
			
			return "usuarios/viewimg";
			
		}

		return "redirect:/usuarios/list";
	}
	
	
	
	
	private int lastPage() {
		Long nReg = usuarioService.count();
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

