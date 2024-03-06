import dayjs from 'dayjs/esm';

import { IBorrowedBook, NewBorrowedBook } from './borrowed-book.model';

export const sampleWithRequiredData: IBorrowedBook = {
  id: 43026,
};

export const sampleWithPartialData: IBorrowedBook = {
  id: 64297,
};

export const sampleWithFullData: IBorrowedBook = {
  id: 79589,
  borrowDate: dayjs('2023-09-12'),
};

export const sampleWithNewData: NewBorrowedBook = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
