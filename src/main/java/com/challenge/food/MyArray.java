package com.challenge.food;

import java.util.Iterator;

public class MyArray {

	private int size;
	private int lenght;
	private int numbers[];

	public MyArray(int lenght) {
		this.numbers = new int[lenght];
		this.lenght = lenght;
	}

	public void add(int number) {
		if (this.size == lenght) {
			int newArray[] = new int[lenght * 2];

			for (int i = 0; i < size; i++) {
				newArray[i] = numbers[i];
			}

			newArray[size] = number;
			size++;
			numbers = newArray;

		} else {
			numbers[size] = number;
			size++;
		}
	}
	
	
	public void removeAt(int index) {
		if(index == size) {
			numbers[index]= 0;
			size--;
		}else if(index <= size && index >= 0) {
			numbers[index] = 0;
			size--;
			for(int i = index; i < size; i++) {
				numbers[i]= numbers[i + 1];
				numbers[i + 1]=0;
			}
		}
	}
	
	public int indexOf(int value) {
		for (int i = 0; i < size; i++) {
			if (numbers[i] == value) {
				return i;
			}
		}
		return -1;
	}
	
	public void print() {
		for(int i = 0; i<size; i++) {
			System.out.print(numbers[i] + " ");		
		}
	}
}