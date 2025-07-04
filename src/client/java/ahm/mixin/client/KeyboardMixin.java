package ahm.mixin.client;

import ahm.keymaps.KeymapRecorder;
import ahm.keymaps.KeymapSearch;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(Keyboard.class)
public class KeyboardMixin {
	@Inject(method = "onKey", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/InputUtil;fromKeyCode(II)Lnet/minecraft/client/util/InputUtil$Key;"), cancellable = true)
    private void onKey(long window, int keycode, int scancode, int action, int modifiers, CallbackInfo info) {
		// Press action
		if (action != 1) {
			return;
		}

		InputUtil.Key key = InputUtil.fromKeyCode(keycode, scancode);
		Screen currentScreen = MinecraftClient.getInstance().currentScreen;
		if (!(currentScreen == null || currentScreen instanceof GameMenuScreen gameMenuScreen && !gameMenuScreen.shouldShowMenu())) {
			return;
		}

		if (KeymapSearch.is_searching()) {
			info.cancel();
			KeymapSearch.update(key);
		} else if (KeymapRecorder.is_recording()) {
			info.cancel();
			KeymapRecorder.update(key);
		} else if (KeymapSearch.keybind_start.boundKey.equals(key)) {
			KeymapSearch.set_is_searching(true);
		}
	}
}
