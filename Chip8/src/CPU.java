//TODO: Maybe have to change all the times where ints are called to do bitwise & 0xFF to stop sign shenanigans?
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
	 * 16 16-bit values for the stack.
	 */
	private short[] stack;
	
	/**
	 * It's a stack pointer.
	 */
	private byte stackPointer;
	
	/**
	 * 16-bit program counter.
	 */
	private short programCounter;
	
	/**
	 * Initialize CPU object, copy ROM to memory starting at 0x200.
	 * @param ROM
	 */
	public CPU(byte[] ROM){
		
	}
	
	/**
	 * Get instruction to be run. Instructions are 2 bytes long.
	 */
	private short fetch(){
		return (short) ((short) (memory[programCounter] << 8) | (memory[programCounter] & 0x00FF));
	}
	/**
	 * Decode and run the instruction itself.
	 */
	private void decodeAndRun(short opCode){
		byte firstDigit = BitwiseHelpers.getFirstDigit(opCode);
		byte lastDigit = BitwiseHelpers.getLastDigit(opCode);
		byte thirdDigit = BitwiseHelpers.getThirdDigit(opCode);
		switch(firstDigit){
		case 0x0:
			if(lastDigit == 0xE){
				returnFrom();
				return;
			} else {
				clearScreen();
				return;
			}
		case 0x1:
			jumpTo(opCode);
			return;
		case 0x2:
			callSubRoutine(opCode);
			return;
		case 0x3:
			skipEqualsByte(opCode);
			return;
		case 0x4:
			skipNotEqualsByte(opCode);
			return;
		case 0x5:
			skipXEqualsY(opCode);
			return;
		case 0x6:
			setVXByte(opCode);
			return;
		case 0x7:
			addVXByte(opCode);
			return;
		case 0x8:
			switch(lastDigit){
			case 0x0:
				setVXVY(opCode);
				return;
			case 0x1:
				setVXOR(opCode);
				return;
			case 0x2:
				setVXAND(opCode);
				return;
			case 0x3:
				setVXXOR(opCode);
				return;
			case 0x4:
				addVXVY(opCode);
				return;
			case 0x5:
				subVXVY(opCode);
				return;
			case 0x6:
				copyVYRightShift(opCode);
				return;
			case 0x7:
				subtractVYVX(opCode);
				return;
			case 0xE:
				copyVYLeftShift(opCode);
				return;
			default: //do nothing
			}
		case 0x9:
			skipVXVYNotEqual(opCode);
			return;
		case 0xA:
			setAddressRegister(opCode);
			return;
		case 0xB:
			jumpPlusV0(opCode);
			return;
		case 0xC:
			randomVXBitAND(opCode);
			return;
		case 0xD:
			drawSprite(opCode);
			return;
		case 0xE: //Maybe this won't handle wrong opcodes when it should??
			if(lastDigit == 0xE){
				skipKeyPressed(opCode);
				return;
			} else {
				skipKeyNotPressed(opCode);
			}
		case 0xF:
			switch(thirdDigit){
			case 0x0:
				if(lastDigit == 0x7){
					setVXDelayTimer(opCode);
					return;
				} else {
					storeKeyPress(opCode);
				}
			case 0x1:
				switch(lastDigit){
				case 0x5:
					setDelayTimer(opCode);
					return;
				case 0x8:
					setSoundTimer(opCode);
					return;
				case 0xE:
					addVXToAddressReg(opCode);
					return;
				default: //Do Nothing
				}
			case 0x2:
				setSpriteLocationToI(opCode);
				return;
			case 0x3:
				setBCD(opCode);
				return;
			case 0x5:
				storeV0VXatI(opCode);
				return;
			case 0x6: 
				loadV0VXatI(opCode);
				return;
			default: //Do nothing
			}
			return;
		default: //do nothing its invalid
		}
		
	}
	/**
	 * Use this to initialize sound, controls etc.
	 */
	public void initializeEmulation(){
		
	}
	/**
	 * Infinite loop that will actually constantly call fetch/decode/run to make the game run.
	 * Screen updates every 1/60th of a second, so wait until that time to actually execute.
	 */
	public void runGameLoop(){
		
	}
	/**
	 * Address 00E0
	 * TODO: Finish this method
	 */
	public void clearScreen(){
		
	}
	/**
	 * Address 00EE
	 */
	public void returnFrom(){
		programCounter = stack[stackPointer];
		stackPointer = (byte)(stackPointer - 0x01);
	}
	/**
	 * Address 1NNN - Jumps to NNN
	 * @param address
	 */
	public void jumpTo(short address){
		programCounter = (short)(0x0FFF & address);
	}
	/**
	 * Address 2NNN - calls subroutine at NNN
	 * @param address
	 */
	public void callSubRoutine(short address){
		stackPointer = (byte)(stackPointer + 0x01);
		stack[stackPointer] = programCounter;
		programCounter = BitwiseHelpers.getLastThreeDigits(address);
	}
	/**
	 * Address 3XNN - skips instruction if VX == NN
	 * @param address
	 */
	public void skipEqualsByte(short address){
		byte x = BitwiseHelpers.getSecondDigit(address);
		byte nN = BitwiseHelpers.getLastTwoDigits(address);
		if(nN == registers[x]){
			programCounter += 0x2;
		}
		programCounter += 0x2;
	}
	/**
	 * Address 4XNN - skips instruction if VX != NN
	 * @param address
	 */
	public void skipNotEqualsByte(short address){
		byte x = BitwiseHelpers.getSecondDigit(address);
		byte nN = BitwiseHelpers.getLastTwoDigits(address);
		if(nN != registers[x]){
			programCounter += 0x2;
		}
		programCounter += 0x2;
	}
	/**
	 * Address 5XY0 - skips instruction if VX == VY
	 * @param address
	 */
	public void skipXEqualsY(short address){
		byte x = BitwiseHelpers.getSecondDigit(address);
		byte y = BitwiseHelpers.getThirdDigit(address);
		if(registers[x] == registers[y]){
			programCounter += 0x2;
		}
		programCounter += 0x2;
	}
	/**
	 * Address 6XNN - sets VX = NN
	 * @param address
	 */
	public void setVXByte(short address){
		byte x = BitwiseHelpers.getSecondDigit(address);
		byte nN = BitwiseHelpers.getLastTwoDigits(address);
		registers[x] = nN;
		programCounter += 0x2;
	}
	/**
	 * 7XNN - Adds NN to VX, no carry flag change.
	 * @param address
	 */
	public void addVXByte(short address){
		byte x = BitwiseHelpers.getSecondDigit(address);
		byte nN = BitwiseHelpers.getLastTwoDigits(address);
		registers[x] += nN;
		programCounter += 0x0002;
	}
	/**
	 * 8XY0 - Sets VX to VY
	 * @param address
	 */
	public void setVXVY(short address){
		byte x = BitwiseHelpers.getSecondDigit(address);
		byte y = BitwiseHelpers.getThirdDigit(address);
		registers[x] = registers[y];
		programCounter += 0x2;
	}
	/**
	 * 8XY1 - Sets VX to VX | VY
	 * @param address
	 */
	public void setVXOR(short address){
		byte x = BitwiseHelpers.getSecondDigit(address);
		byte y = BitwiseHelpers.getThirdDigit(address);
		registers[x] = (byte) (registers[x] | registers[y]);
		programCounter += 0x2;
	}
	/**
	 * 8XY2 - Sets VX to VX & VY
	 * @param address
	 */
	public void setVXAND(short address){
		byte x = BitwiseHelpers.getSecondDigit(address);
		byte y = BitwiseHelpers.getThirdDigit(address);
		registers[x] = (byte) (registers[x] & registers[y]);
		programCounter += 0x2;
	}
	/**
	 * 8XY3 - Sets VX to VX ^ VY
	 * @param address
	 */
	public void setVXXOR(short address){
		byte x = BitwiseHelpers.getSecondDigit(address);
		byte y = BitwiseHelpers.getThirdDigit(address);
		registers[x] = (byte) (registers[x] ^ registers[y]);
		programCounter += 0x2;
	}
	/**
	 * 8XY4 - Adds VY to VX, set VF to 1 if carry.
	 * @param address
	 */
	public void addVXVY(short address){
		byte x = BitwiseHelpers.getSecondDigit(address);
		byte y = BitwiseHelpers.getThirdDigit(address);
		if(((int)registers[x] & 0xFF) + ((int)registers[y] & 0xFF) > 255){ //the & 0xFF is just in case java throws in some garbage
			registers[0xF] = 1;
		}
		registers[x] += registers[y];
		programCounter += 0x2;
	}
	/**
	 * 8XY5 - Subtracts VY from VX, set VF to 0 if borrow, 1 if no borrow
	 * @param address
	 */
	public void subVXVY(short address){
		byte x = BitwiseHelpers.getSecondDigit(address);
		byte y = BitwiseHelpers.getThirdDigit(address);
		if(registers[x] > registers[y]){ 
			registers[0xF] = 1;
		}
		registers[x] -= registers[y];
		programCounter += 0x2;
	}
	/**
	 * 8XY6 - Shift VY right 1, copy to VX, store LSB in VF.
	 * TODO: Two implementations, figure out which one is the proper one??
	 * @param address
	 */
	public void copyVYRightShift(short address){
		/*byte x = BitwiseHelpers.getSecondDigit(address);
		byte y = BitwiseHelpers.getThirdDigit(address);
		byte LSB = BitwiseHelpers.getLastDigit(registers[y]);
		registers[0xF] = LSB;
		*/
	}
	/**
	 * 8XY7 - Set VX to VY-VX, VF = 0 when borrow, 1 if no borrow.
	 * @param address
	 */
	public void subtractVYVX(short address){
		byte x = BitwiseHelpers.getSecondDigit(address);
		byte y = BitwiseHelpers.getThirdDigit(address);
		if(registers[y] > registers[x]){
			registers[0xF] = 1;
		}
		registers[x] = (byte) (registers[y] - registers[x]);
		programCounter += 0x2;
	}
	/**
	 * 8XYE - Set VX to VY shifted left 1, VF has MSB of VY.
	 * TODO: Figure out what is the actual implementation of this
	 * @param address
	 */
	public void copyVYLeftShift(short address){
		
	}
	/**
	 * 9XY0 - Skips next instruction if VX != VY
	 * @param address
	 */
	public void skipVXVYNotEqual(short address){
		byte x = BitwiseHelpers.getSecondDigit(address);
		byte y = BitwiseHelpers.getThirdDigit(address);
		if(registers[x] != registers[y]){
			programCounter += 0x2;
		}
		programCounter += 0x2;
	}
	/**
	 * ANNN - Set I to NNN.
	 * @param address
	 */
	public void setAddressRegister(short address){
		short nNN = BitwiseHelpers.getLastThreeDigits(address);
		addressRegister = nNN;
		programCounter += 0x2;
	}
	/**
	 * BNNN - Jump to address NNN + V0
	 * @param address
	 */
	public void jumpPlusV0(short address){
		short nNN = BitwiseHelpers.getLastThreeDigits(address);
		programCounter = (short) (nNN + registers[0]);
	}
	/**
	 * CXNN - Set VX to NN & random(0,255)
	 * @param address
	 */
	public void randomVXBitAND(short address){
		byte nN = BitwiseHelpers.getLastTwoDigits(address);
		byte x = BitwiseHelpers.getSecondDigit(address);
		byte random = (byte)(Math.random()*256);
		registers[x] = (byte) (nN & random);
		programCounter += 0x2;
	}
	/**
	 * DXYN - Draws a sprite at (VX,VY) width = 8, height = N.
	 * Each row of 8 pixels is read as bit-coded starting from I, dont change I after
	 * VF set to 1 if anything goes from set to unset, 0 otherwise.
	 * @param address
	 */
	public void drawSprite(short address){
		programCounter += 0x2;
	}
	/**
	 * EX9E - Skips next instruction if key in VX is pressed.
	 * @param address
	 */
	public void skipKeyPressed(short address){
		programCounter += 0x2;
	}
	/**
	 * EXA1 - Skips next instruction if key in VX isn't pressed.
	 * @param address
	 */
	public void skipKeyNotPressed(short address){
		programCounter += 0x2;
	}
	/**
	 * FX07 - Sets VX to delay timer value
	 * @param address
	 */
	public void setVXDelayTimer(short address){
		programCounter += 0x2;
	}
	/**
	 * FX0A - Sets VX to key press that is waited for
	 * @param address
	 */
	public void storeKeyPress(short address){
		programCounter += 0x2;
	}
	/**
	 * FX15 - Sets delay timer to VX
	 * @param address
	 */
	public void setDelayTimer(short address){
		programCounter += 0x2;
	}
	/**
	 * FX18 - Sets sound timer to VX
	 * @param address
	 */
	public void setSoundTimer(short address){
		programCounter += 0x2;
	}
	/**
	 * FX1E -  Adds VX to AddressRegister
	 * @param address
	 */
	public void addVXToAddressReg(short address){
		byte x = BitwiseHelpers.getSecondDigit(address);
		addressRegister += registers[x];
		programCounter += 0x2;
	}
	/**
	 * FX29 - Set's I to location of sprite for character in VX. Characters in hex are represented by 4x5 font.
	 * @param address
	 */
	public void setSpriteLocationToI(short address){
		programCounter += 0x2;
	}
	/**
	 * FX33 - Takes binary representation of VX, sets I = hundreds, I+1 = tens, and I+2 = ones.
	 * TODO: Finish this method
	 * @param address
	 */
	public void setBCD(short address){
		programCounter += 0x2;
	}
	/**
	 * FX55 - Stores values from V0 to VX inclusive in memory starting at address I. Increment I for each write.
	 * TODO: Finish this method
	 * @param address
	 */
	public void storeV0VXatI(short address){
		programCounter += 0x2;
	}
	/**
	 * FX65 - Loads values starting from I into V0 to VX inclusive. Increment I for each load.
	 * TODO: Finish this method
	 * @param address
	 */
	public void loadV0VXatI(short address){
		programCounter += 0x2;
	}
}
