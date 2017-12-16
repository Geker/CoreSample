package org.springframework.samples.mvc.fileupload;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.mvc.extensions.ajax.AjaxUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/fileupload")
public class FileUploadController {

    @ModelAttribute
    public void ajaxAttribute(WebRequest request, Model model) {
        model.addAttribute("ajaxRequest", AjaxUtils.isAjaxRequest(request));
    }

    @RequestMapping(method = RequestMethod.GET)
    public void fileUploadForm() {
    }

    @RequestMapping(method = RequestMethod.POST)
    public void processUpload(@RequestParam MultipartFile file, Model model) throws IOException {
        file.getBytes();
        try (OutputStream output = new FileOutputStream("D:\\upload\\" + file.getOriginalFilename())) {
            IOUtils.copy(file.getInputStream(), output);
            model.addAttribute("message", "File '" + file.getOriginalFilename() + "' uploaded successfully");
        }
    }

}
