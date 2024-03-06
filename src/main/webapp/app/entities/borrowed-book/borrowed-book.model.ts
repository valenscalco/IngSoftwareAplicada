import dayjs from 'dayjs/esm';
import { IBook } from 'app/entities/book/book.model';
import { IClient } from 'app/entities/client/client.model';

export interface IBorrowedBook {
  id: number;
  borrowDate?: dayjs.Dayjs | null;
  book?: Pick<IBook, 'id' | 'name'> | null;
  client?: Pick<IClient, 'id' | 'email'> | null;
}

export type NewBorrowedBook = Omit<IBorrowedBook, 'id'> & { id: null };
