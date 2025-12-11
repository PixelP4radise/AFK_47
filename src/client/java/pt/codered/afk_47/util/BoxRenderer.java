package pt.codered.afk_47.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import org.joml.Matrix4f;

import java.util.List;

@Environment(EnvType.CLIENT)
public final class BoxRenderer {

    private BoxRenderer() {
    }

    /**
     * Draw outlined boxes (transparent interior) around each BlockPos.
     *
     * @param matrices provider MatrixStack from the render event
     * @param provider VertexConsumerProvider from the render event (context.consumers())
     * @param blocks   list of BlockPos
     * @param r        red 0..1
     * @param g        green 0..1
     * @param b        blue 0..1
     * @param a        alpha 0..1
     */
    public static void renderBoxes(MatrixStack matrices,
                                   VertexConsumerProvider provider,
                                   List<BlockPos> blocks,
                                   float r, float g, float b, float a) {
        if (blocks == null || blocks.isEmpty()) return;

        Camera cam = MinecraftClient.getInstance().gameRenderer.getCamera();
        double cx = cam.getPos().x;
        double cy = cam.getPos().y;
        double cz = cam.getPos().z;

        // Use the lines render layer (matches vertex format)
        VertexConsumer vc = provider.getBuffer(RenderLayer.getLines());
        Matrix4f mat = matrices.peek().getPositionMatrix();

        for (BlockPos pos : blocks) {
            Box box = new Box(
                    pos.getX(), pos.getY(), pos.getZ(),
                    pos.getX() + 1.0, pos.getY() + 1.0, pos.getZ() + 1.0
            ).offset(-cx, -cy, -cz);

            drawBoxLines(vc, mat, box, r, g, b, a);
        }
    }

    private static void drawBoxLines(VertexConsumer vc, Matrix4f mat, Box b,
                                     float r, float g, float bl, float a) {
        float x1 = (float) b.minX;
        float y1 = (float) b.minY;
        float z1 = (float) b.minZ;
        float x2 = (float) b.maxX;
        float y2 = (float) b.maxY;
        float z2 = (float) b.maxZ;

        // bottom quad (4 edges)
        line(vc, mat, x1, y1, z1, x2, y1, z1, r, g, bl, a);
        line(vc, mat, x2, y1, z1, x2, y1, z2, r, g, bl, a);
        line(vc, mat, x2, y1, z2, x1, y1, z2, r, g, bl, a);
        line(vc, mat, x1, y1, z2, x1, y1, z1, r, g, bl, a);

        // top quad
        line(vc, mat, x1, y2, z1, x2, y2, z1, r, g, bl, a);
        line(vc, mat, x2, y2, z1, x2, y2, z2, r, g, bl, a);
        line(vc, mat, x2, y2, z2, x1, y2, z2, r, g, bl, a);
        line(vc, mat, x1, y2, z2, x1, y2, z1, r, g, bl, a);

        // vertical edges
        line(vc, mat, x1, y1, z1, x1, y2, z1, r, g, bl, a);
        line(vc, mat, x2, y1, z1, x2, y2, z1, r, g, bl, a);
        line(vc, mat, x2, y1, z2, x2, y2, z2, r, g, bl, a);
        line(vc, mat, x1, y1, z2, x1, y2, z2, r, g, bl, a);
    }

    /**
     * Add two vertices for a line. Note: we do NOT call endVertex()/next() — the VertexConsumer API
     * used in 1.21.5 finalizes vertices implicitly as shown in the interface you posted.
     */
    private static void line(VertexConsumer vc, Matrix4f mat,
                             float x1, float y1, float z1,
                             float x2, float y2, float z2,
                             float r, float g, float b, float a) {

        // first vertex (starts/records vertex attributes)
        vc.vertex(mat, x1, y1, z1)   // transforms position using matrix
                .color(r, g, b, a)
                .normal(0f, 1f, 0f);       // normal can be whatever you want for lines

        // second vertex — starting this new vertex implicitly finalizes the previous one
        vc.vertex(mat, x2, y2, z2)
                .color(r, g, b, a)
                .normal(0f, 1f, 0f);
    }
}