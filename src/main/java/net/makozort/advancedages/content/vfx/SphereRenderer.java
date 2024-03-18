package net.makozort.advancedages.content.vfx;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import team.lodestar.lodestone.handlers.RenderHandler;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class SphereRenderer {

    private static List<Sphere> spheres;

    private static float previousTick;

    private static List<Sphere> scheduledForRemoval;

    public static Vec3 latestCameraPos;



    public static void RenderSpheres(RenderLevelStageEvent event){
        if(spheres == null) spheres = new ArrayList<>();
        if(scheduledForRemoval == null) scheduledForRemoval = new ArrayList<>();


        latestCameraPos = event.getCamera().getPosition();
        Vec3 cameraPos = latestCameraPos;

        float deltaTick = (event.getRenderTick() + event.getPartialTick()) - previousTick;
        previousTick = (event.getRenderTick() + event.getPartialTick());

        sortSpheres();
        for(Sphere sphere: spheres){
            if(!sphere.isValid(deltaTick)){
                markForRemoval(sphere);
                continue;
            }
            PoseStack stack = event.getPoseStack();
            Vec3 spherePos = sphere.pos;
            float radius = sphere.radius;
            RenderType type = sphere.type;
            float opacity =1f;
            if (sphere instanceof TimerGrowingSphere)
                opacity = .5f;




            stack.pushPose();
            stack.translate(spherePos.x - cameraPos.x, spherePos.y - cameraPos.y, spherePos.z - cameraPos.z);
            VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat();
            builder.setAlpha(opacity).renderSphere(RenderHandler.DELAYED_RENDER.getBuffer(type), stack, radius, 20, 20);
            stack.popPose();
        }
        emptySchedule();
    }


    public static void addSphere(Sphere sphere){
        if(spheres == null) spheres = new ArrayList<>();
        spheres.add(sphere);
    }


    public static void clear(){
        spheres = null;
    }


    public static void markForRemoval(Sphere sphere){
        scheduledForRemoval.add(sphere);
    }


    public static void emptySchedule() {
        for (Sphere i : scheduledForRemoval) {
            spheres.remove(i);
        }
        scheduledForRemoval.clear();
    }


    public static void sortSpheres(){
        spheres.sort(new SphereComparator());
    }


    public static class Sphere{
        public RenderType type;
        public Vec3 pos;
        public float radius;
        public boolean isValid;

        public Sphere(RenderType type, Vec3 pos, float radius){
            this.type = type;
            this.pos = pos;
            this.radius = radius;
            this.isValid = true;
        }

        public boolean isValid(float deltaTick){
            return isValid;
        }
    }

    public static class TimerSphere extends Sphere{
        public int maxTime;
        public float lifeTime = 0;

        public TimerSphere(RenderType type, Vec3 pos, float radius, int maxTime) {
            super(type, pos, radius);
            this.maxTime = maxTime;
        }

        @Override
        public boolean isValid(float deltaTick) {
            lifeTime += deltaTick;
            if(lifeTime > maxTime) return false;
            return true;
        }
    }

    public static class TimerGrowingSphere extends TimerSphere{
        public float minRadius;
        public float maxRadius;

        public TimerGrowingSphere(RenderType type, Vec3 pos, float minRadius, float maxRadius, int maxTime) {
            super(type, pos, minRadius, maxTime);
            this.minRadius = minRadius;
            this.maxRadius = maxRadius;
        }

        @Override
        public boolean isValid(float deltaTick) {
            this.radius = minRadius + (lifeTime/maxTime) * (maxRadius - minRadius);
            lifeTime += deltaTick;
            if(lifeTime > maxTime) return false;
            return true;
        }
    }

    public static class OrbitalSphere extends Sphere{
        public Vec3 orbitCentre;
        public float orbitRadius;
        public int fullOrbitTime;
        public float lifeTime = 0;


        public OrbitalSphere(RenderType type, Vec3 orbitCentre, float sphereRadius, float orbitRadius, int fullOrbitTime) {
            super(type, orbitCentre.add(orbitRadius, 0, 0), sphereRadius);
            this.orbitCentre = orbitCentre;
            this.orbitRadius = orbitRadius;
            this.fullOrbitTime = fullOrbitTime;
        }

        @Override
        public boolean isValid(float deltaTick) {
            this.lifeTime += deltaTick;
            this.pos = orbitCentre.add(orbitRadius*Math.cos((lifeTime*2*Math.PI)/fullOrbitTime), 0, orbitRadius* Math.sin((lifeTime*2*Math.PI)/(fullOrbitTime)));
            return true;
        }
    }

    public static class SphereComparator implements Comparator<Sphere> {
        @Override
        public int compare(Sphere o1, Sphere o2) {
            double distance1 = o1.pos.distanceTo(SphereRenderer.latestCameraPos);
            double distance2 = o2.pos.distanceTo(SphereRenderer.latestCameraPos);
            if(distance1 == distance2){
                return 0;
            }if(distance1 > distance2){
                return -1;
            }else {
                return 1;
            }
        }
    }
}
