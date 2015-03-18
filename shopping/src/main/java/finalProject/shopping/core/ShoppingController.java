package finalProject.shopping.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import finalProject.shopping.model.ShoppingCart;

@RestController
@RequestMapping("/shopping")
public class ShoppingController {
	//@Autowired
	ShoppingService service;

	public ShoppingController() {
		this.service = new ShoppingServiceImpl();
	}

	@RequestMapping(value = "/{shoppingCartId}", method = RequestMethod.GET)
	public ResponseEntity<String> getStringCartByGET(
			@PathVariable Long shoppingCartId) {
		ShoppingCart cart = service.findOneShoppingCart(shoppingCartId);
		if (cart != null) {
			return new ResponseEntity<String>(cart.toString(), HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Cart not found", HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/json={shoppingCartId}", method = RequestMethod.GET)
	public ResponseEntity<ShoppingCart> getJsoncCartByGET(
			@PathVariable Long shoppingCartId) {
		ShoppingCart cart = service.findOneShoppingCart(shoppingCartId);
		return new ResponseEntity<ShoppingCart>(cart, HttpStatus.OK);
	}

	@RequestMapping(value = "/hi", method = RequestMethod.GET)
	public ResponseEntity<String> sayHi() {
		if (service == null) {
			return new ResponseEntity<String>(":(", HttpStatus.OK);
		} else {
			String aux = service.toString();
			return new ResponseEntity<String>(aux, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public ResponseEntity<String> useDB() {
		try {
			ShoppingCart cart = service.findOneShoppingCart(1L);
			return new ResponseEntity<String>("Query made", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("too bad", HttpStatus.OK);
		}
	}

	/*
	 * @RequestMapping(value = "/add", method = RequestMethod.GET) public String
	 * addMeetingByGET(
	 * 
	 * @RequestParam(value = "desc") String description,
	 * 
	 * @RequestParam(value = "day") String day,
	 * 
	 * @RequestParam(value = "room") String roomId,
	 * 
	 * @RequestParam(value = "from") String from,
	 * 
	 * @RequestParam(value = "to") String to) { if (service.addItemToCart(item,
	 * cart)) { return ("New meeting added."); } else { return
	 * ("There was an error. Meeting has not been added."); } }
	 * 
	 * 
	 * 
	 * @RequestMapping(value = "/cancel/{meetingId}", method =
	 * RequestMethod.GET) public String deleteMeetingByGET(@PathVariable Long
	 * meetingId) { if (meetingService.cancelMeeting(meetingId)) { return
	 * ("Meeting canceled."); } else { return
	 * ("There was an error. Meeting has not been canceled."); } }
	 * 
	 * @RequestMapping(value = "/update/{meetingId}", method =
	 * RequestMethod.GET) public String updateMeetingByGET(
	 * 
	 * @PathVariable Long meetingId,
	 * 
	 * @RequestParam(value = "desc", defaultValue = "") String description,
	 * 
	 * @RequestParam(value = "day", defaultValue = "") String day,
	 * 
	 * @RequestParam(value = "room", defaultValue = "") String roomId,
	 * 
	 * @RequestParam(value = "from", defaultValue = "") String from,
	 * 
	 * @RequestParam(value = "to", defaultValue = "") String to) { if
	 * (meetingService.updateMeeting(meetingId, description, roomId, day, from,
	 * to)) { return ("Meeting updated."); } else { return
	 * ("There was an error. Meeting has not been updated."); } }
	 * 
	 * @RequestMapping(value = "/add/{meetingId}/{attendeeId}", method =
	 * RequestMethod.GET) public String addAttendeeToMeetingByGET(@PathVariable
	 * Long meetingId,
	 * 
	 * @PathVariable Long attendeeId) { if
	 * (meetingService.addAttendeeToMeeting(meetingId, attendeeId)) { return
	 * ("Meeting updated."); } else { return
	 * ("There was an error. Attendee has not been added to Meeting."); } }
	 */
}
