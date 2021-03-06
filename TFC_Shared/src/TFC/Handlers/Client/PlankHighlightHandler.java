package TFC.Handlers.Client;

import org.lwjgl.opengl.GL11;

import TFC.*;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Settings;
import TFC.Core.TFC_Time;
import TFC.Core.Player.PlayerInfo;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.Items.*;
import TFC.TileEntities.TileEntityWoodConstruct;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class PlankHighlightHandler{

	@ForgeSubscribe
	public void DrawBlockHighlightEvent(DrawBlockHighlightEvent evt) 
	{
		World world = evt.player.worldObj;		
		double var8 = evt.player.lastTickPosX + (evt.player.posX - evt.player.lastTickPosX) * (double)evt.partialTicks;
		double var10 = evt.player.lastTickPosY + (evt.player.posY - evt.player.lastTickPosY) * (double)evt.partialTicks;
		double var12 = evt.player.lastTickPosZ + (evt.player.posZ - evt.player.lastTickPosZ) * (double)evt.partialTicks;


		if(evt.currentItem != null && evt.currentItem.getItem() instanceof ItemPlank)
		{

			//Setup GL for the depthbox
			//GL11.glEnable(GL11.GL_BLEND);
			//GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			//GL11.glColor3f(1,1,1);
			//GL11.glDisable(GL11.GL_CULL_FACE);
			//GL11.glDepthMask(false);
			//ForgeHooksClient.bindTexture("/bioxx/woodoverlay.png", ModLoader.getMinecraftInstance().renderEngine.getTexture("/bioxx/woodoverlay.png"));

//			double blockMinX = evt.target.blockX;
//			double blockMinY = evt.target.blockY;
//			double blockMinZ = evt.target.blockZ;
//			double blockMaxX = evt.target.blockX+1;
//			double blockMaxY = evt.target.blockY+1;
//			double blockMaxZ = evt.target.blockZ+1;


			//			drawFaceUV(AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(
			//					blockMinX,
			//					blockMinY,
			//					blockMinZ,
			//					blockMaxX,
			//					blockMaxY, 
			//					blockMaxZ
			//					).expand(0.002F, 0.002F, 0.002F).getOffsetBoundingBox(-var8, -var10, -var12), evt.target.sideHit);

			GL11.glDisable(GL11.GL_TEXTURE_2D);

			boolean isConstruct = world.getBlockId(evt.target.blockX, evt.target.blockY, evt.target.blockZ) == TFCBlocks.WoodConstruct.blockID;
			float div = 1f / TileEntityWoodConstruct.PlankDetailLevel;
			//Get the hit location in local box coords
			double hitX = Math.round((evt.target.hitVec.xCoord - evt.target.blockX)*100)/100.0d;
			double hitY = Math.round((evt.target.hitVec.yCoord - evt.target.blockY)*100)/100.0d;
			double hitZ = Math.round((evt.target.hitVec.zCoord - evt.target.blockZ)*100)/100.0d;

			//get the targeted sub block coords
			double subX = (double)((int)((hitX)*8))/8;
			double subY = (double)((int)((hitY)*8))/8;
			double subZ = (double)((int)((hitZ)*8))/8;

			//create the box size
			double minX = evt.target.blockX + subX;
			double minY = evt.target.blockY + subY;
			double minZ = evt.target.blockZ + subZ;
			double maxX = minX + 0.125;
			double maxY = minY + 0.125;
			double maxZ = minZ + 0.125;

			if(isConstruct && hitY != 0  && hitY != 1 && hitZ != 0  && hitZ != 1 && hitX != 0  && hitX != 1)
			{
				if(evt.target.sideHit == 0)
				{
					minY = evt.target.blockY;
					maxY = evt.target.blockY + 1;
				}
				else if(evt.target.sideHit == 1)
				{
					minY = evt.target.blockY;
					maxY = evt.target.blockY + 1;
				}
				else if(evt.target.sideHit == 2)
				{
					minZ = evt.target.blockZ;
					maxZ = evt.target.blockZ+1;
				}
				else if(evt.target.sideHit == 3)
				{
					minZ = evt.target.blockZ;
					maxZ = evt.target.blockZ+1;
				}
				else if(evt.target.sideHit == 4)
				{
					minX = evt.target.blockX;
					maxX = evt.target.blockX+1;
				}
				else if(evt.target.sideHit == 5)
				{
					minX = evt.target.blockX;
					maxX = evt.target.blockX+1;
				}
			}
			else
			{
				if(evt.target.sideHit == 0)
				{
					maxY = minY;
					minY = minY - 1;
				}
				else if(evt.target.sideHit == 1)
				{
					maxY = minY + 1;
				}
				else if(evt.target.sideHit == 2)
				{
					maxZ = minZ;
					minZ = minZ - 1;
				}
				else if(evt.target.sideHit == 3)
				{
					maxZ = minZ + 1;
				}
				else if(evt.target.sideHit == 4)
				{
					maxX = minX;
					minX = minX - 1;
				}
				else if(evt.target.sideHit == 5)
				{
					maxX = minX + 1;
				}
			}

			//Setup GL for the depthbox
			GL11.glEnable(GL11.GL_BLEND);
			//Setup the GL stuff for the outline
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.4F);
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glDepthMask(false);
			//Draw the mini Box
			drawBox(AxisAlignedBB.getAABBPool().getAABB(minX,minY,minZ,maxX,maxY,maxZ).expand(0.002F, 0.002F, 0.002F).getOffsetBoundingBox(-var8, -var10, -var12));

			GL11.glDepthMask(true);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_BLEND);

		}
	}

	void drawFaceUV(AxisAlignedBB par1AxisAlignedBB, int side)
	{
		Tessellator var2 = Tessellator.instance;

		var2.setColorRGBA_F(1, 1, 1, 1);
		//Top
		var2.startDrawing(GL11.GL_QUADS);

		if(side == 0)
		{
			var2.addVertexWithUV(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ, 0, 0);
			var2.addVertexWithUV(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ, 1, 0);
			var2.addVertexWithUV(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ, 1, 1);
			var2.addVertexWithUV(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ, 0, 1);
		}
		else if(side == 1)
		{
			var2.addVertexWithUV(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ, 0, 0);
			var2.addVertexWithUV(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ, 1, 0);
			var2.addVertexWithUV(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ, 1, 1);
			var2.addVertexWithUV(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ, 0, 1);
		}
		else if(side == 2)
		{
			var2.addVertexWithUV(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ, 0, 0);
			var2.addVertexWithUV(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ, 1, 0);
			var2.addVertexWithUV(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ, 1, 1);
			var2.addVertexWithUV(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ, 0, 1);
		}
		else if(side == 3)
		{
			var2.addVertexWithUV(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ, 0, 0);
			var2.addVertexWithUV(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ, 1, 0);
			var2.addVertexWithUV(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ, 1, 1);
			var2.addVertexWithUV(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ, 0, 1);
		}
		else if(side == 4 )
		{
			var2.addVertexWithUV(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ, 0, 0);
			var2.addVertexWithUV(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ, 1, 0);
			var2.addVertexWithUV(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ, 1, 1);
			var2.addVertexWithUV(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ, 0, 1);
		}
		else if( side == 5)
		{
			var2.addVertexWithUV(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ, 0, 0);
			var2.addVertexWithUV(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ, 1, 0);
			var2.addVertexWithUV(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ, 1, 1);
			var2.addVertexWithUV(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ, 0, 1);
		}
		var2.draw();
	}

	void drawFace(AxisAlignedBB par1AxisAlignedBB)
	{
		Tessellator var2 = Tessellator.instance;

		//Top
		var2.startDrawing(GL11.GL_QUADS);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.draw();
	}

	void drawBox(AxisAlignedBB par1AxisAlignedBB)
	{
		Tessellator var2 = Tessellator.instance;

		//Top
		var2.startDrawing(GL11.GL_QUADS);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.draw();

		//Bottom
		var2.startDrawing(GL11.GL_QUADS);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.draw();

		//-x
		var2.startDrawing(GL11.GL_QUADS);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.draw();

		//+x
		var2.startDrawing(GL11.GL_QUADS);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.draw();

		//-z
		var2.startDrawing(GL11.GL_QUADS);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.draw();

		//+z
		var2.startDrawing(GL11.GL_QUADS);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.draw();
	}

	void drawOutlinedBoundingBox(AxisAlignedBB par1AxisAlignedBB)
	{
		Tessellator var2 = Tessellator.instance;
		var2.startDrawing(3);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.draw();
		var2.startDrawing(3);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.draw();
		var2.startDrawing(GL11.GL_LINES);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.draw();
	}

}
