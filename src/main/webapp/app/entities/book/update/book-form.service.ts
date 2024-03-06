import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IBook, NewBook } from '../book.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IBook for edit and NewBookFormGroupInput for create.
 */
type BookFormGroupInput = IBook | PartialWithRequiredKeyOf<NewBook>;

type BookFormDefaults = Pick<NewBook, 'id' | 'authors'>;

type BookFormGroupContent = {
  id: FormControl<IBook['id'] | NewBook['id']>;
  isbn: FormControl<IBook['isbn']>;
  name: FormControl<IBook['name']>;
  publishYear: FormControl<IBook['publishYear']>;
  copies: FormControl<IBook['copies']>;
  cover: FormControl<IBook['cover']>;
  coverContentType: FormControl<IBook['coverContentType']>;
  publisher: FormControl<IBook['publisher']>;
  authors: FormControl<IBook['authors']>;
};

export type BookFormGroup = FormGroup<BookFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class BookFormService {
  createBookFormGroup(book: BookFormGroupInput = { id: null }): BookFormGroup {
    const bookRawValue = {
      ...this.getFormDefaults(),
      ...book,
    };
    return new FormGroup<BookFormGroupContent>({
      id: new FormControl(
        { value: bookRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      isbn: new FormControl(bookRawValue.isbn, {
        validators: [Validators.required, Validators.minLength(5), Validators.maxLength(13)],
      }),
      name: new FormControl(bookRawValue.name, {
        validators: [Validators.required, Validators.maxLength(100)],
      }),
      publishYear: new FormControl(bookRawValue.publishYear, {
        validators: [Validators.required, Validators.minLength(4), Validators.maxLength(50)],
      }),
      copies: new FormControl(bookRawValue.copies, {
        validators: [Validators.required],
      }),
      cover: new FormControl(bookRawValue.cover),
      coverContentType: new FormControl(bookRawValue.coverContentType),
      publisher: new FormControl(bookRawValue.publisher),
      authors: new FormControl(bookRawValue.authors ?? []),
    });
  }

  getBook(form: BookFormGroup): IBook | NewBook {
    return form.getRawValue() as IBook | NewBook;
  }

  resetForm(form: BookFormGroup, book: BookFormGroupInput): void {
    const bookRawValue = { ...this.getFormDefaults(), ...book };
    form.reset(
      {
        ...bookRawValue,
        id: { value: bookRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): BookFormDefaults {
    return {
      id: null,
      authors: [],
    };
  }
}
