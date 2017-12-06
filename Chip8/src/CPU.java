
public class CPU {
	/**
	 * Memory of size 0xFFF (4096) 
	 */
	private byte[] memory = new byte[0xFFF];
	
	/**
	 * 16 registers from V0 - VF. Avoid writing to VF as it is a flag for some instructions.
	 */
	private byte[] registers = new byte[16];
	
	/**
	 * Also called I, 16 bits wide.
	 */
	private short addressRegister;
	
	/**
	 * 16-bit program counter.
	 */
	private short programCounter;
	
	/**
	 * Address 00E0
	 */
	public void clearScreen(){
		
	}
	/**
	 * Address 00EE
	 */
	public void returnFrom(){
		
	}
	/**
	 * Address 1NNN - Jumps to NNN
	 * @param address
	 */
	public void jumpTo(short address){
		
	}
	/**
	 * Address 2NNN - calls subroutine at NNN
	 * @param address
	 */
	public void callSubRoutine(short address){
		
	}
	/**
	 * Address 3XNN - skips instruction if VX == NN
	 * @param address
	 */
	public void skipEqualsByte(short address){
		
	}
	/**
	 * Address 4XNN - skips instruction if VX != NN
	 * @param address
	 */
	public void skipNotEqualsByte(short address){
		
	}
	/**
	 * Address 5XY0 - skips instruction if VX == VY
	 * @param address
	 */
	public void skipXEqualsY(short address){
		
	}
	/**
	 * Address 6XNN - sets VX = NN
	 * @param address
	 */
	public void setVXByte(short address){
		
	}
	/**
	 * 7XNN - Adds NN to VX, no carry flag change.
	 * @param address
	 */
	public void addVXByte(short address){
		
	}
	/**
	 * 8XY0 - Sets VX to VY
	 * @param address
	 */
	public void setVXVY(short address){
		
	}
	/**
	 * 8XY1 - Sets VX to VX | VY
	 * @param address
	 */
	public void setVXOR(short address){
		
	}
	/**
	 * 8XY2 - Sets VX to VX & VY
	 * @param address
	 */
	public void setVXAND(short address){
		
	}
	/**
	 * 8XY3 - Sets VX to VX ^ VY
	 * @param address
	 */
	public void setVXXOR(short address){
		
	}
	/**
	 * 8XY4 - Adds VX to VY, set VF to 1 if carry.
	 * @param address
	 */
	public void addVXVY(short address){
		
	}
	/**
	 * 8XY5 - Subtracts VY from VX, set VF to 0 if borrow, 1 if no borrow
	 * @param address
	 */
	public void subVXVY(short address){
		
	}
	/**
	 * 8XY6 - Shift VY right 1, copy to VX, store LSB in VF.
	 * @param address
	 */
	public void copyVYRightShift(short address){
		
	}
	/**
	 * 8XY7 - Set VX to VY-VX, VF = 0 when borrow, 1 if no borrow.
	 * @param address
	 */
	public void subtractVYVX(short address){
		
	}
	/**
	 * 8XYE - Set VX to VY shifted left 1, VF has MSB of VY.
	 * @param address
	 */
	public void copyVYLeftShift(short address){
		
	}
	/**
	 * 9XY0 - Skips next instruction if VX != VY
	 * @param address
	 */
	public void skipVXVYNotEqual(short address){
		
	}
	/**
	 * ANNN - Set I to NNN.
	 * @param address
	 */
	public void setAddressRegister(short address){
		
	}
	/**
	 * BNNN - Jump to address NNN + V0
	 * @param address
	 */
	public void jumpPlusV0(short address){
		
	}
	/**
	 * CXNN - Set VX to NN & random(0,255)
	 * @param address
	 */
	public void randomVXBitAND(short address){
		
	}
	/**
	 * DXYN - Draws a sprite at (VX,VY) width = 8, height = N.
	 * Each row of 8 pixels is read as bit-coded starting from I, dont change I after
	 * VF set to 1 if anything goes from set to unset, 0 otherwise.
	 * @param address
	 */
	public void drawSprite(short address){
		
	}
	/**
	 * EX9E - Skips next instruction if key in VX is pressed.
	 * @param address
	 */
	public void skipKeyPressed(short address){
		
	}
	/**
	 * EXA1 - Skips next instruction if key in VX isn't pressed.
	 * @param address
	 */
	public void skipKeyNotPressed(short address){
		
	}
	/**
	 * FX07 - Sets VX to delay timer value
	 * @param address
	 */
	public void setVXDelayTimer(short address){
		
	}
	/**
	 * FX0A - Sets VX to key press that is waited for
	 * @param address
	 */
	public void storeKeyPress(short address){
		
	}
	/**
	 * FX15 - Sets delay timer to VX
	 * @param address
	 */
	public void setDelayTimer(short address){
		
	}
	/**
	 * FX18 - Sets sound timer to VX
	 * @param address
	 */
	public void setSoundTimer(short address){
		
	}
	/**
	 * FX1E -  Adds VX to AddressRegister
	 * @param address
	 */
	public void addVXToAddressReg(short address){
		
	}
	/**
	 * FX29 - Set's I to location of sprite for character in VX. Characters in hex are represented by 4x5 font.
	 * @param address
	 */
	public void setSpriteLocationToI(short address){
		
	}
	/**
	 * FX33 - Takes binary representation of VX, sets I = hundreds, I+1 = tens, and I+2 = ones.
	 * @param address
	 */
	public void setBCD(short address){
		
	}
	/**
	 * FX55 - Stores values from V0 to VX inclusive in memory starting at address I. Increment I for each write.
	 * @param address
	 */
	public void storeV0VXatI(short address){
		
	}
	/**
	 * FX65 - Loads values starting from I into V0 to VX inclusive. Increment I for each load.
	 * @param address
	 */
	public void loadV0VXatI(short address){
		
	}
}
