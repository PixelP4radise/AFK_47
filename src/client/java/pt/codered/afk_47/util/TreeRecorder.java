package pt.codered.afk_47.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import pt.codered.afk_47.model.data.TreePosition;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TreeRecorder {

    private static final TreeRecorder INSTANCE = new TreeRecorder();
    private boolean isRecording = false;

    // We use a Map to group Logs under a specific Root
    // LinkedHashMap preserves insertion order (Root 1 -> Root 2 -> ...)
    private final Map<BlockPos, List<BlockPos>> treeData = new LinkedHashMap<>();

    // Tracks which root is currently "Selected" to receive new logs
    private BlockPos currentActiveRoot = null;

    public static TreeRecorder get() {
        return INSTANCE;
    }

    // --- 1. TOGGLE ---
    public void toggleRecording() {
        isRecording = !isRecording;
        MinecraftClient mc = MinecraftClient.getInstance();

        if (isRecording) {
            // Start Clean
            treeData.clear();
            currentActiveRoot = null;
            mc.inGameHud.getChatHud().addMessage(Text.of("§a[Recorder] Started. Step 1: Stand on a block and press Add Root."));
        } else {
            // Stop & Print
            printResult();
            mc.inGameHud.getChatHud().addMessage(Text.of("§c[Recorder] Stopped. Check console."));
        }
    }

    // --- 2. ADD ROOT (Sets the active group) ---
    public void addRoot() {
        if (!isRecording) return;
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return;

        BlockPos pos = mc.player.getBlockPos().down();

        // If this root doesn't exist yet, start a new list for it
        if (!treeData.containsKey(pos)) {
            treeData.put(pos, new ArrayList<>());
            mc.inGameHud.getChatHud().addMessage(Text.of("§2[+] New Root Group: " + pos.toShortString()));
        } else {
            mc.inGameHud.getChatHud().addMessage(Text.of("§e[!] Selected existing Root: " + pos.toShortString()));
        }

        // Set this as the active root for subsequent logs
        currentActiveRoot = pos;
    }

    // --- 3. ADD LOG (Associated with active Root) ---
    public void addLog() {
        if (!isRecording) return;
        MinecraftClient mc = MinecraftClient.getInstance();

        if (currentActiveRoot == null) {
            mc.inGameHud.getChatHud().addMessage(Text.of("§c[!] Error: No Root selected! Add a Root first."));
            return;
        }

        HitResult hit = mc.player.raycast(10.0, 0.0f, false);
        if (hit.getType() == HitResult.Type.BLOCK) {
            BlockPos pos = ((BlockHitResult) hit).getBlockPos();

            List<BlockPos> logsForRoot = treeData.get(currentActiveRoot);

            if (!logsForRoot.contains(pos)) {
                logsForRoot.add(pos);
                mc.inGameHud.getChatHud().addMessage(Text.of("§4[+] Log added to current root."));
            }
        }
    }

    // --- OUTPUT GENERATOR ---
    private void printResult() {
        if (treeData.isEmpty()) return;

        String name = "Tree_" + System.currentTimeMillis() % 1000;
        StringBuilder sb = new StringBuilder();

        // Format: new Tree("Name", List.of( ... positions ... ))
        sb.append("new Tree(\"").append(name).append("\", List.of(\n");

        int count = 0;
        for (Map.Entry<BlockPos, List<BlockPos>> entry : treeData.entrySet()) {
            BlockPos root = entry.getKey();
            List<BlockPos> logs = entry.getValue();

            // Format: new TreePosition(new BlockPos(x,y,z), List.of(...))
            sb.append("    new TreePosition(")
                    .append(formatPos(root)).append(", ")
                    .append("List.of(").append(formatList(logs)).append("))");

            if (++count < treeData.size()) sb.append(",\n");
        }
        sb.append("\n));");

        System.out.println(sb.toString());

        MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(
                Text.of("§eData printed to console.")
        );
    }

    private String formatPos(BlockPos p) {
        return String.format("new BlockPos(%d, %d, %d)", p.getX(), p.getY(), p.getZ());
    }

    private String formatList(List<BlockPos> list) {
        return list.stream().map(this::formatPos).collect(Collectors.joining(", "));
    }

    // --- RENDERING HELPERS ---

    // Flatten the map keys for rendering
    public List<BlockPos> getRootsToRender() {
        return isRecording ? new ArrayList<>(treeData.keySet()) : List.of();
    }

    // Flatten all values for rendering
    public List<BlockPos> getLogsToRender() {
        if (!isRecording) return List.of();
        return treeData.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public List<TreePosition> getRecordedPositions() {
        if (!isRecording) return List.of();

        return treeData.entrySet().stream()
                .map(entry -> new TreePosition(entry.getKey(), entry.getValue()))
                .toList();
    }
}