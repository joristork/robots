#ifndef __CTYPE_H
#define __CTYPE_H

/* The MPLAB-C17 versions are for the library as distributed with MPLAB-C17
 * v2.30.03. As the library for MPLAB-C17 is updated, the prototypes will be
 * converted to match the MPLAB-C18 versions more closely.
 */

#if    __18CXX
#define PARAM_SCLASS auto
#else /* 17CXX */
#define PARAM_SCLASS static
#endif

/** @name Character Testing Functions
 * The character testing functions test whether a character value is a
 * member of a set of characters specified in the description of the
 * function. The functions return non-zero if and only if the character
 * tested is in the specified set.
 *
 */
/*@{*/
/** @name isalnum
 * The {\bf isalnum} function tests the value of {\bf c} to determine if
 * it is an alpha-numeric character. An alph-numeric character is defined
 * as any value for which {\bf isalpha} or {\bf isdigit} is true.
 */
#if __18CXX
int isalnum (PARAM_SCLASS int c);
#else
char isalnum(PARAM_SCLASS char);
#endif

/** @name isalpha
 * The {\bf isalpha} function tests the value of {\bf c} to determine if it
 * is an alphabetic character. An alphabetic character is defined as any
 * value for which {\bf isupper} or {\bf islower} is true.
 */
#if __18CXX
int isalpha (PARAM_SCLASS int c);
#else
char isalpha(PARAM_SCLASS char);
#endif

/** @name isascii
 * The {\bf isascii} function tests the value of {\bf c} to determine if it
 * is an ascii character. An ascii character is defined as any character
 * in the set [0,7f].
 */
#if __18CXX
#else
char isascii(PARAM_SCLASS char);
#endif

/** @name iscntrl
 * The {\bf iscntrl} function tests the value of {\bf c} to determine if it
 * is a control character. A control character is defined as any character
 * in the set [0,1f] or the character 7f.
 */
#if __18CXX
int iscntrl (PARAM_SCLASS int c);
#else
char iscntrl(PARAM_SCLASS char);
#endif

/** @name isdigit
 * The {\bf isdigit} function tests the value of {\bf c} to determine if it
 * is a digit character. A digit character is defined as any decimal digit
 * character (0, 1, 2, 3, 4, 5, 6, 7, 8, 9).
 */
#if __18CXX
int isdigit (PARAM_SCLASS int c);
#else
char isdigit(PARAM_SCLASS char);
#endif

/** @name isgraph
 * The {\bf isgraph} function tests the value of {\bf c} determine if it
 * is a graphical character. A graphical character is defined as any
 * printing character except space (' ').
 */
#if __18CXX
int isgraph (PARAM_SCLASS int c);
#endif

/** @name islower
 * The {\bf islower} function tests the value of {\bf c} determine if it
 * is a lower-case character. A lower case letter is defined as any character
 * in the set ['a','z'].
 */
#if __18CXX
int islower (PARAM_SCLASS int c);
#else
char islower(PARAM_SCLASS char);
#endif

/** @name isprint
 * The {\bf isprint} function tests the value of {\bf c} determine if it
 * is a printable character. A printable character is defined as any character
 * in the set [0x20,0x7e].
 */
#if __18CXX
int isprint (PARAM_SCLASS int c);
#endif

/** @name ispunct
 * The {\bf ispunct} function tests the value of {\bf c} determine if it
 * is a punctuation character. A punctuation character is defined as any character
 * for which {\bf isprint} is true and {\bf isalnum} is not true.
 */
#if __18CXX
int ispunct (PARAM_SCLASS int c);
#endif

/** @name isspace
 * The {\bf isspace} function tests the value of {\bf c} determine if it
 * is a white-space character. A white-space character is defined as a
 * character value of space, form feed, new-line, carriage-return, 
 * horizontal tag, or vertical tab.
 */
#if __18CXX
int isspace (PARAM_SCLASS int c);
#endif

/** @name isupper
 * The {\bf isupper} function tests the value of {\bf c} determine if it
 * is a upper-case character. A upper case letter is defined as any character
 * in the set ['A','Z'].
 */
#if __18CXX
int isupper (PARAM_SCLASS int c);
#else
char isupper(PARAM_SCLASS char);
#endif

/** @name isdigit
 * The {\bf isdigit} function tests the value of {\bf c} to determine if it
 * is a hex-digit character. A hex-digit character is defined as any 
 * hexadecimal digit character (0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d, e, f,
 * A, B, C, D, E, F).
 */
#if __18CXX
int isxdigit (PARAM_SCLASS int c);
#else
char isxdigit(PARAM_SCLASS char);
#endif

/*@}*/
/** @name Character Case Mapping Functions
 */
/*@{*/
/** @name toascii
 * The {\bf ascii} function converts a non-ascii character to an ascii
 * character by clearing the most-significant bit of {\bf c}.
 * @return If {\bf isascii} is true for the value of {\bf c}, the value
 * of {\bf c} is returned unchanged, else the converted value is returned.
 */
#if __18CXX
#else
char toascii(PARAM_SCLASS char);
#endif

/** @name tolower
 * The {\bf tolower} function converts an upper case character to the
 * corresponding lower case character.
 * @return If {\bf isupper} is true for the value of {\bf c}, the corresponding
 * lower case equivalent is returned, else the value of {\bf c} is returned
 * unchanged.
 */
#if __18CXX
int tolower (PARAM_SCLASS int c);
#else
char tolower(PARAM_SCLASS char);
#endif

/** @name toupper
 * The {\bf toupper} function converts a lower case character to the
 * corresponding upper case character.
 * @return If {\bf islower} is true for the value of {\bf c}, the corresponding
 * upper case equivalent is returned, else the value of {\bf c} is returned
 * unchanged.
 */
#if __18CXX
int toupper (PARAM_SCLASS int c);
#else
char toupper(PARAM_SCLASS char);
#endif

/*@}*/

#endif /* __CTYPE_H */
