package submc.habitats;

public class HabitatListener {

	////////////////THIS CLASS IS ONLY HERE FOR REFERENCE////////////////////////
//	
//	@EventHandler
//	public void onHabitatBuilder(PlayerInteractEvent e) {
//		Player p = e.getPlayer();
//		if(e.getHand() == EquipmentSlot.OFF_HAND)return;
//		if(e.getItem() != null && ItemUtils.isSimilar(e.getItem(), ItemUtils.getHabitatBuilder(1))) {
//			if(e.getAction() == Action.LEFT_CLICK_BLOCK) {
//				 Block b = e.getClickedBlock();
//				 //TODO IF CLICKED BLOCK IS ATTACH POINT
//				 if(HabitatBuilder.hasSelected(p)) {
//					 if(SubMC.getHabitatManager().hasHabitat(p)) {
//						 Habitat h = SubMC.getHabitatManager().getHabitat(p);
//						 if(h.isAttachPoint(b)) {
//							 
//						 }else {
//							 p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder().color(net.md_5.bungee.api.ChatColor.RED).append("##########").create());
//							 //play sound?
//						 }
//					 }else {
//						 
//					 }
//					 
//					 if(HabitatBuilder.getSelected(p).canPlace(b.getLocation(), Direction.valueOf(e.getBlockFace()))) {
//						 //build habitat
//						 p.sendMessage("can place");
//					 }
//				 }
//			}else if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
//				HabitatBuilder.openHabitatBuilder(p);
//			}
//		}
//	}

	
	
}