import { IBook, NewBook } from './book.model';

export const sampleWithRequiredData: IBook = {
  id: 37098,
  isbn: 'Jewelery',
  name: 'Account',
  publishYear: 'Rhode bandwidth Fresh',
  copies: 85793,
};

export const sampleWithPartialData: IBook = {
  id: 81264,
  isbn: 'Steel bandwid',
  name: 'invoice',
  publishYear: 'Streamlined programming',
  copies: 33983,
};

export const sampleWithFullData: IBook = {
  id: 96361,
  isbn: 'Music Hampshi',
  name: 'Bedfordshire copying',
  publishYear: 'invoice Salad Senior',
  copies: 66464,
  cover: '../fake-data/blob/hipster.png',
  coverContentType: 'unknown',
};

export const sampleWithNewData: NewBook = {
  isbn: 'facilitate Co',
  name: 'Loan',
  publishYear: 'face Hat Credit',
  copies: 3601,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
