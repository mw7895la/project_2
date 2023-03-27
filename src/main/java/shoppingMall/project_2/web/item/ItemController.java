package shoppingMall.project_2.web.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shoppingMall.project_2.domain.item.*;
import shoppingMall.project_2.domain.member.Member;
import shoppingMall.project_2.service.ItemService;
import shoppingMall.project_2.web.SessionConst;
import shoppingMall.project_2.web.file.FileStore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final FileStore fileStore;

    @GetMapping("/add")
    public String addForm(@ModelAttribute Item item) {
        return "items/addForm";
    }

    @PostMapping("/add")
    public String addItem(@Validated @ModelAttribute ItemSaveForm saveForm, BindingResult bindingResult, HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException {

        if (saveForm.getPrice() * saveForm.getQuantity() < 10000) {
            bindingResult.reject(null, "금액 총 합이 10000원을 넘겨야 합니다.");
            return "items/addForm";
        }

        UploadFile attachFile = fileStore.storeFile(saveForm.getAttachFile());
        List<UploadFile> storeImageFiles = fileStore.storeFiles(saveForm.getImageFiles());

        HttpSession session = request.getSession(false);
        Member sessionMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        Item item = new Item();
        item.setItemName(saveForm.getItemName());
        item.setPrice(saveForm.getPrice());
        item.setQuantity(saveForm.getQuantity());
        item.setImage(attachFile.getStoreFileName());
        item.setAttachFile(attachFile);
        item.setImageFiles(storeImageFiles);
        item.setUserId(sessionMember.getUserId());

        Item savedItem = itemService.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/items/{itemId}";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }

/*    @GetMapping("/attach/{itemId}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long itemId) {
        itemService.findById(itemId);
    }*/

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {

        Item findItem = itemService.findById(itemId).get();

        model.addAttribute("item", findItem);
        return "items/item";
    }

    @GetMapping
    public String items(@Validated @ModelAttribute("itemSearch") ItemSearchCond cond, BindingResult bindingResult, Model model) {
        List<Item> items = itemService.findAll(cond);
        model.addAttribute("items", items);
        return "items/items";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable("itemId") Long itemId, Model model) {
        Item findItem = itemService.findById(itemId).get();
        model.addAttribute("item", findItem);
        return "items/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String editItem(@PathVariable("itemId") Long itemId, @ModelAttribute ItemUpdateForm updateForm,RedirectAttributes redirectAttributes) {
        itemService.update(itemId, updateForm);
        redirectAttributes.addAttribute("itemId", itemId);

        return "redirect:/items/{itemId}";
    }
}
